/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.shared.data.serialize;

import io.muenchendigital.digiwf.legacy.dms.muc.process.mapper.MetadataProcessDataMapper;
import io.muenchendigital.digiwf.legacy.form.domain.model.FieldTypes;
import io.muenchendigital.digiwf.legacy.form.domain.model.FormField;
import org.springframework.stereotype.Component;

/**
 * Serialize a alw document.
 *
 * @author externer.dl.horn
 */
@Component
public class AlwDocumentSerializeHandler extends DocumentSerializeHandler {

    public AlwDocumentSerializeHandler(final MetadataProcessDataMapper metadataMapper) {
        super(metadataMapper);
    }

    @Override
    public Boolean isResponsibleFor(final FormField formField) {
        return FieldTypes.ALW_DOCUMENT_INPUT.equals(formField.getType());
    }

}