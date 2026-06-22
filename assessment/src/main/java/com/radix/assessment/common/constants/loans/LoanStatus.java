/**
 * LoanStatus is an enum that defines the 2 possible lifecycle states of the loan. It is used to determine whether a
 * loan is currently active and able to accept payments,
 * or has been fully repaid and settled.
 *
 * @author Kayen Joshua Vengethasamy
 * @since 2026-06-19
 */

package com.radix.assessment.common.constants.loans;

public enum LoanStatus {
//    Status	Description
//    ACTIVE	The loan has a remaining balance greater than zero and can accept payment
//    SETTLED	The loan does not have a balance can not accept payment
    ACTIVE,
    SETTLED

}
