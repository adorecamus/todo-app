package com.umin.todoapp.infra.s3

import software.amazon.awssdk.services.s3.S3Client
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region

@Configuration
class S3Config(
    @Value("\${cloud.s3.accessKeyId}")
    private val accessKeyId: String,
    @Value("\${cloud.s3.secretAccessKey}")
    private val secretAccessKey: String,
    @Value("\${cloud.s3.region}")
    private val region: String
) {
    @Bean
    fun amazonS3Client(): S3Client {
        val credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey)

        return S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build()
    }
}