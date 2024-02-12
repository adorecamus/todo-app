package com.umin.todoapp.infra.s3

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetUrlRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Service
class S3Service(
    @Value("\${cloud.s3.bucket}")
    private val bucket: String,
    private val s3Client: S3Client
) {

    fun putObject(file: MultipartFile, path: String): String {
        val objectKey = "$path${UUID.randomUUID()}${file.originalFilename}"

        val request = PutObjectRequest.builder()
            .bucket(bucket)
            .key(objectKey)
            .contentType(file.contentType)
            .contentLength(file.size)
            .build()

        check(
            s3Client.putObject(request, RequestBody.fromBytes(file.bytes))
                .sdkHttpResponse().statusText().getOrNull().equals("OK")
        ) { "uploading file to s3 failed" }

        return getObjectUrl(objectKey)
    }

    fun getObjectUrl(objectKey: String): String {
        return s3Client.utilities().getUrl(
            GetUrlRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .build()
        ).toString()
    }

}