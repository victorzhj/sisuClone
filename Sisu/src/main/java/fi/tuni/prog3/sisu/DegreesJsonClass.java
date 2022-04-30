package fi.tuni.prog3.sisu;

import java.util.List;
/**
 * A class that stores every degree that you can get from the Sisu api call: https://sis-tuni.funidata.fi/kori/api/module-search?curriculumPeriodId=uta-lvv-2021&universityId=tuni-university-root-id&moduleType=DegreeProgramme&limit=1000
 */
public class DegreesJsonClass {
    public Integer start;
    public Integer limit;
    public Integer total;
    public List<SearchResult> searchResults = null;

    /**
     * Returns a list with data of every degree.
     * @return List<SearchResult> a list with data of every degree.
     */
    public List<SearchResult> getSearchResulst() {
        return this.searchResults;
    }

    /**
     * A helper class that represent one degree. Not to be confused with degreesProgrammeData because that stores the degree data with more information.
     */
    public class SearchResult {
        public String id;
        public String code;
        public String lang;
        public String groupId;
        public String name;
        public String nameMatch;
        public String searchTagsMatch;
        public List<String> curriculumPeriodIds = null;
        public Credits credits;

        /**
         * Return this degree's groupId.
         * @return String this degree's groupId.
         */
        public String getGroupId() {
            return this.groupId;
        }

        /**
         * Return this degree's name.
         * @return String this degree's name.
         */
        public String getName() {
            return this.name;
        }

        /**
         * Return this degree's credits.
         * @return String this degree's credits.
         */
        public Credits getCreditsObject() {
            return this.credits;
        }
    }

    /**
     * A helper class that represent one degree's credits.
     */
    public class Credits {
        public Integer min;
        public Object max;

        /**
         * Return degree's target credits in format of min-max, e.g, "3-5". 
         * if no max then only in min-, e.g "3-".
         * @return String degree's target credits.
         */
        public String getCredits() {
            if (this.max == null){
                return this.min.toString() + "-";
            } else {
                return this.min.toString() + "-" + this.max.toString();
            }
        }
    }
}
