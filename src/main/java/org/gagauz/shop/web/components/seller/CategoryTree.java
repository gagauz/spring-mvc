package org.gagauz.shop.web.components.seller;

import java.util.List;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.gagauz.shop.database.model.ProductCategory;

public class CategoryTree {
    @Parameter
    private List<ProductCategory> categories;

    @SetupRender
    boolean setupRender() {
        return null != categories;
    }

    void beginRender(MarkupWriter writer) {
        writer.writeRaw("<ul>");
        for (ProductCategory category : categories) {
            printCategory(category, writer);
        }
        writer.writeRaw("</ul>");
    }

    private void printCategory(ProductCategory category, MarkupWriter writer) {
        writer.writeRaw("<li><div>");
        writer.writeRaw(category.getExternalId());
        writer.writeRaw(" ");
        writer.writeRaw(category.getName());
        writer.writeRaw("</div>");
        if (category.getChildren().size() > 0) {
            writer.writeRaw("<ul>");
            for (ProductCategory category2 : category.getChildren()) {
                printCategory(category2, writer);
            }
            writer.writeRaw("</ul>");
        }

        writer.writeRaw("</li>");
    }
}
