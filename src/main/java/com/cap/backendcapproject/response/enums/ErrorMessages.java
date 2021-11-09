package com.cap.backendcapproject.response.enums;

public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("Champ obligatoire manquant."),
    RECORD_ALREADY_EXISTS("L'enregistrement existe déjà."),
    INTERNAL_SERVER_ERROR("Erreur interne du serveur."),
    NO_RECORD_FOUND("L'enregistrement avec l'identifiant fourni est introuvable.");

    private String errorMessages;

     ErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }

    public String getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }
}
