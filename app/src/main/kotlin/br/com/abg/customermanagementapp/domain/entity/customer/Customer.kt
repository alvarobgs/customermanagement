package br.com.abg.customermanagementapp.domain.entity.customer

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "tb_customer", indexes = [Index(name = "ix_document", columnList = "document"), Index(name = "ix_name", columnList = "name")])
class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "name", nullable = false, length = 80)
    var name: String? = null

    @Column(name = "document", nullable = false, unique = true, length = 20)
    var document: String? = null

    @Column(name = "phone", nullable = false, length = 20)
    var phone: String? = null

    @Column(name = "born_date", nullable = false)
    var bornDate: LocalDate? = null

    @Column(name = "creation_date", nullable = false)
    var creationDate: LocalDateTime? = null

    @OneToOne(cascade = [ CascadeType.ALL ], orphanRemoval = true)
    @JoinColumn(name = "address_id", foreignKey = ForeignKey(name = "fk_customer_address"))
    var address: Address? = null
}