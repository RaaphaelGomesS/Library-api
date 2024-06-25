package com.raphael.Library.utils;

import com.raphael.Library.entities.Requisition;
import com.raphael.Library.exception.AssociateException;
import com.raphael.Library.exception.RequisitionException;
import org.springframework.http.HttpStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    public static void verifyEmail(String email) throws AssociateException {

        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new AssociateException("O Email é inválido!", HttpStatus.CONFLICT);
        }
    }

    public static void verifyPassword(String password) throws AssociateException {

        Pattern pattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,15}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            throw new AssociateException("A senha deve ter pelo menos uma letra maiuscula, um número, um caracter especial e estar entre 8 e 15 caracteres!", HttpStatus.CONFLICT);
        }
    }

    public static void verifyManyRequisitionHave(Requisition newReq) throws Exception {

        if (newReq.getAssociate().getBooksInPossession().size() >= 3) {
            throw new AssociateException("O máximo de livros em posse são 3!", HttpStatus.CONFLICT);
        }

        for (Requisition requisition : newReq.getAssociate().getBooksInPossession()) {
            if (requisition.getBook().getBookId() == newReq.getBook().getBookId()) {
                throw new RequisitionException("O Associado já está em posse desse livro!", HttpStatus.CONFLICT);
            }
        }
    }
}
