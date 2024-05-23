package com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.source;

import com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements;
import com.thomsonreuters.codes.codesbench.quality.tests.sourcenavigateangular.assertions.SourceNavigateAngularAssertions;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.BrowserAnnotations.EDGE;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.UserAnnotations.LEGAL;
import com.thomsonreuters.codes.codesbench.quality.utilities.annotations.CustomAnnotations.LogAnnotations.LOG;
import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularContextMenuItemsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularFiltersAndSortsPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.SourceNavigateAngularPageElements.*;
import static com.thomsonreuters.codes.codesbench.quality.pageelements.sourcenavigateangular.popups.SourceNavigateAngularToastPageElements.DISPLAYING_MULTIPLE_AND_DUPLICATE_RENDITIONS;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * User Story 723059: [HALCYONST-16987] [CMI]View - Multiple and Duplicate Renditions
 */
public class MultipleDuplicateRenditionsTests extends SourceNavigateAngularAssertions
{

    @Test
    @DisplayName("View: Multiple and Duplicate Renditions")
    @EDGE
    @LEGAL
    @LOG
    public void filterForMultipleDuplicateRenditionsTest()
    {
        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);

        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeader(SourceNavigateAngularPageElements.MULTIPLE_DUPLICATES);
        sourceNavigateAngularFiltersAndSortsPage().openFilterTabColumnHeader();
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(SELECT_ALL);
        assertThat(sourceNavigateAngularPage().getNumberOfRowsDisplayedInGrid(RENDITIONS_GRID_CONTAINER))
                .as("The grid should be empty")
                        .isEqualTo(0);

        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(BLANKS);

        assertThat(sourceNavigateAngularPage().doesElementExist(format(GRID_ROW_MULTIPLE_DUPLICATE_CELL_VALUE, 0)))
                .as("The first rendition should not have neither multiple nor duplicate flag")
                .isFalse();
        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextMenuItem(VIEW);
        sourceNavigateAngularPage().breakOutOfFrame();
        assertThat(sourceNavigateAngularPage().getElementsAttribute(VIEW_MULTIPLE_AND_DUPLICATE_RENDITIONS + CMI_ANCESTOR_DIV, "aria-disabled"))
                .as("The context menu item should be disabled")
                .isEqualTo("true");

        sourceNavigateAngularFiltersAndSortsPage().openMenuColumnHeader(SourceNavigateAngularPageElements.MULTIPLE_DUPLICATES);
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);

        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(BLANKS);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(MULTIPLE_FLAG);
        sourceNavigateAngularFiltersAndSortsPage().clickCheckbox(DUPLICATE_FLAG);

        assertThat(sourceNavigateAngularPage().getElementsAttribute(format(GRID_ROW_MULTIPLE_DUPLICATE_CELL_VALUE, 0), "alt"))
                .as("The first rendition should have either multiple or duplicate flag")
                        .containsAnyOf(MULTIPLE_FLAG, DUPLICATE_FLAG);

        sourceNavigateAngularPage().click(FIRST_RENDITION_ROW);
        sourceNavigateAngularPage().rightClickRenditions();
        sourceNavigateAngularPage().clickContextSubMenuItem(VIEW, VIEW_MULTIPLE_AND_DUPLICATE_RENDITIONS);

        assertThatMessage(TOAST_MESSAGE, DISPLAYING_MULTIPLE_AND_DUPLICATE_RENDITIONS );
        sourceNavigateAngularPage().makePause();
        assertThatTotalRenditionsNumberIsEqualToNumberOfDisplayedRenditions();
        for (int i = 0; i < sourceNavigateAngularPage().getNumberOfRowsDisplayedInGrid(RENDITIONS_GRID_CONTAINER); i++)
        {
            assertThat(sourceNavigateAngularPage().getElementsAttribute(format(GRID_ROW_MULTIPLE_DUPLICATE_CELL_VALUE, i), "alt"))
                    .as("The first rendition should have either multiple or duplicate flag")
                    .containsAnyOf(MULTIPLE_FLAG, DUPLICATE_FLAG);
        }

        List<String> columnHeadersList = Arrays.asList(
                SourceNavigateAngularPageElements.CORRELATION_ID,
                SourceNavigateAngularPageElements.DOCUMENT_UUID
        );

        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Correlation ID");
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Document UUID");
        sourceNavigateAngularLeftSidePanePage().filterForColumnAndSelect("Rendition UUID");
        sourceNavigateAngularPage().scrollPageHorizontallyShiftEnd(FIRST_RENDITION_ROW);

        columnHeadersList
                .forEach(
                        s -> {
                            foundUuids = sourceNavigateAngularPage().getCellValue(RENDITIONS_GRID_CONTAINER, COLUMN_CELL_VALUE_OF_RENDITION_ROW_PATTERN, s);
                            assertThat(Collections.frequency(foundUuids, foundUuids.get(0)) == foundUuids.size())
                                    .as("All displayed renditions should have the same %s", s)
                                    .isTrue();
                        }
                );

        assertThat(sourceNavigateAngularPage().doesElementExist(format(COLUMN_FILTER_ICON, SourceNavigateAngularPageElements.RENDITION_UUID)))
                .as("The renditions should be filtered by Rendition UUID")
                .isTrue();
    }
}
