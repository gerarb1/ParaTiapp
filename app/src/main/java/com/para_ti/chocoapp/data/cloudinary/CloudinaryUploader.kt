package com.para_ti.chocoapp.data.cloudinary

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils

object CloudinaryUploader {

    private val cloudinary = Cloudinary(
        ObjectUtils.asMap(
            "cloud_name", "TU_CLOUD_NAME",
            "api_key", "TU_API_KEY",
            "api_secret", "TU_API_SECRET"
        )
    )

    suspend fun uploadImage(imagePath: String): String? {
        return try {
            val uploadResult = cloudinary.uploader().upload(imagePath, ObjectUtils.emptyMap())
            uploadResult["secure_url"] as? String
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
