package br.com.abg.domain.entity.customer

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "tb_address")
class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "zip_code", nullable = false, length = 8)
    val zipCode: String? = null

    @Column(name = "number", nullable = false, length = 10)
    val number: String? = null

    @Column(name = "street", nullable = false, length = 255)
    val street: String? = null

    @Column(name = "neighborhood", nullable = false, length = 50)
    val neighborhood: String? = null

    @Column(name = "city", nullable = false, length = 50)
    val city: String? = null

    @Column(name = "state", nullable = false, length = 2)
    val state: String? = null
}