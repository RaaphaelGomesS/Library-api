package com.raphael.Library.utils;

import com.raphael.Library.entities.Associate;
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
            throw new AssociateException("O email é inválido.", HttpStatus.CONFLICT);
        }
    }

    public static void verifyPassword(String password) throws AssociateException {

        Pattern pattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,15}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            throw new AssociateException("A senha deve ter pelo menos uma letra maiúscula, uma letra minúscula, um número, um caracter especial e deve estar entre 8 e 15 caracteres.", HttpStatus.CONFLICT);
        }
    }

    public static void verifyManyRequisitionHave(Requisition newReq) throws Exception {

        if (newReq.getAssociate().getBooksInPossession().size() >= 3) {
            throw new AssociateException("Já possui o máximo de requisições possíveis = 3", HttpStatus.BAD_REQUEST);
        }

        for (Requisition requisition : newReq.getAssociate().getBooksInPossession()) {
            if (requisition.getBook().getBookId().equals(newReq.getBook().getBookId())) {
                throw new RequisitionException("Já existe uma requisição com esse livro.", HttpStatus.BAD_REQUEST);
            }
        }
    }

    public static void verifyHasPermission(Associate associateByToken, Associate associate) throws AssociateException {
        if (!(associate.getUsername().equals(associateByToken.getUsername())) && !(associateByToken.getAuthorities().contains("ROLE_ADMIN"))) {
            throw new AssociateException("Não possui permissão.", HttpStatus.FORBIDDEN);
        }
    }
}
