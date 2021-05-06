/*Defining a Hibernate generator at the package level. It is only used in the Course entity, but it seemed
* cleaner to place it here. This is one of the Hibernate value generators that uses the native database sequence
* if available and falls back to creating its own table to simulate a sequence if the native one is not supported.
* Equivalent to GenerationType.SEQUENCE in javax.persistence. */
@org.hibernate.annotations.GenericGenerator(
        name = "ID_GENERATOR",
        strategy = "enhanced-sequence",
        parameters = {
                @org.hibernate.annotations.Parameter(
                        name = "sequence_name",
                        value = "SMS_SEQUENCE"
                )
        }
)
package com.srp.jpa.entitymodels;