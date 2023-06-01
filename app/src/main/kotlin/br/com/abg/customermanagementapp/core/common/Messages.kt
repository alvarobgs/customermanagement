package br.com.abg.customermanagementapp.core.common

import java.util.*

class Messages {
    companion object {
        fun findByKey(key: String, language: Language): String {
            val rb = if (language == Language.PT) ResourceBundle.getBundle("bundle.Messages_ptBR")
                        else ResourceBundle.getBundle("bundle.Messages_enUS")
            return rb.getString(key)
        }

        fun findByKey(key: String): String {
            return findByKey(key, Language.EN)
        }
    }

    enum class Language(val code: String) {

        PT("pt-br"),
        EN("en");

        companion object {
            fun from(key: String): Language {
                values().find { it.code == key }?.let { return it }
                return EN
            }
        }
    }
}