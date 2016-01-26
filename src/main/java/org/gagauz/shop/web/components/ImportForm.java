package org.gagauz.shop.web.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.FieldValidator;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.FieldValidatorSource;
import org.apache.tapestry5.upload.components.Upload;
import org.apache.tapestry5.upload.services.UploadedFile;

import java.util.function.Function;

public class ImportForm {

    @Parameter(required = true, allowNull = false)
    private Function<UploadedFile, Void> importer;

    @Parameter(value = "csv", defaultPrefix = BindingConstants.LITERAL)
    private String extension;

    @Component
    private Form importForm;

    @Component(parameters = {"validate=prop:validators"})
    private Upload file;

    @Property
    private UploadedFile fileValue;

    @Inject
    private FieldValidatorSource fieldValidatorSource;

    public FieldValidator<?> getValidators() {
        return fieldValidatorSource.createValidators(file, "required,extension=" + extension);
    }

    void onSuccessFromImportForm() {
        try {
            importer.apply(fileValue);
        } catch (Exception e) {
            importForm.recordError(file, "Ошибка при импорте файла: " + e.getMessage());
        }
    }
}
