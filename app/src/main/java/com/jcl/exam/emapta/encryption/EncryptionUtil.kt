package com.jcl.exam.emapta.encryption

import com.jcl.exam.emapta.application.MyApplication
import java.io.UnsupportedEncodingException
import java.security.GeneralSecurityException

/**
 * Created by jaylumba on 05/16/2018.
 */
object EncryptionUtil {

    private val isEncrypt = true

    fun encrypt(str: String): String {
        if (isEncrypt) {
            var cipherTextIvMac: AesCbcWithIntegrity.CipherTextIvMac? = null
            try {
                cipherTextIvMac = AesCbcWithIntegrity.encrypt(str, MyApplication.keys)
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            } catch (e: GeneralSecurityException) {
                e.printStackTrace()
            }

            return cipherTextIvMac!!.toString()
        } else {
            return str
        }
    }

    fun decrypt(cipherTextString: String): String {
        var plainText = cipherTextString
        if (isEncrypt) {
            //Use the constructor to re-create the CipherTextIvMac class from the string:
            try {
                val cipherTextIvMac = AesCbcWithIntegrity.CipherTextIvMac(cipherTextString)
                plainText = AesCbcWithIntegrity.decryptString(cipherTextIvMac, MyApplication.keys)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            } catch (e: GeneralSecurityException) {
                e.printStackTrace()
            }

            return plainText
        } else {
            return cipherTextString
        }
    }
}