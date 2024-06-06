package com.raphael.Library.utils;

import com.raphael.Library.entities.Requisition;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.exception.RequisitionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    public static void verifyEmail(String email) throws AssociateException {

        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new AssociateException("O Email é inválido!");
        }
    }

    public static void verifyNumber(String number) throws AssociateException {

        if (number.length() != 11) {
            throw new AssociateException("O número é inválido, deve ter 11 caracteres com o DDD");
        }
    }

    public static void verifyManyRequisitionHave(Requisition newReq) throws Exception {

        if (newReq.getAssociate().getBooksInPossession().size() >= 3) {
            throw new AssociateException("O máximo de livros em posse são 3!");
        }

        for (Requisition requisition : newReq.getAssociate().getBooksInPossession()) {
            if (requisition.getBook().getBookId() == newReq.getBook().getBookId()) {
                throw new RequisitionException("O Associado já está em posse desse livro!");
            }
        }
    }
}
