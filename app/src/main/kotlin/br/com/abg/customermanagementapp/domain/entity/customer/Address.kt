package br.com.abg.customermanagementapp.domain.entity.customer

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
    var id: Long? = null

    @Column(name = "zip_code", nullable = false, length = 8)
    var zipCode: String? = null

    @Column(name = "number", nullable = false, length = 10)
    var number: String? = null

    @Column(name = "street", nullable = false, length = 255)
    var street: String? = null

    @Column(name = "neighborhood", nullable = false, length = 50)
    var neighborhood: String? = null

    @Column(name = "city", nullable = false, length = 50)
    var city: String? = null

    @Column(name = "state", nullable = false, length = 2)
    var state: String? = null
}