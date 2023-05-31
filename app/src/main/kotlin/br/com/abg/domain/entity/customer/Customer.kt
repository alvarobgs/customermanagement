package br.com.abg.domain.entity.customer

import jakarta.persistence.CascadeType
import java.time.LocalDate
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tb_customer")
class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "name", nullable = false, length = 80)
    val name: String? = null

    @Column(name = "document", nullable = false, unique = true, length = 20)
    val document: String? = null

    @Column(name = "phone", nullable = false, length = 20)
    val phone: String? = null

    @Column(name = "born_date", nullable = false)
    val bornDate: LocalDate? = null

    @OneToOne(cascade = [ CascadeType.ALL ], orphanRemoval = true)
    @JoinColumn(name = "address_id", foreignKey = ForeignKey(name = "fk_customer_address"))
    val address: Address? = null
}