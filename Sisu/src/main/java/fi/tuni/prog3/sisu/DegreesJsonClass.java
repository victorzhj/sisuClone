package fi.tuni.prog3.sisu;

import java.util.List;

public class DegreesJsonClass {
    public Integer start;
    public Integer limit;
    public Integer total;
    public List<SearchResult> searchResults = null;

    public List<SearchResult> getSearchResulst() {
        return this.searchResults;
    }

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

        public String getGroupId() {
            return this.groupId;
        }

        public String getName() {
            return this.name;
        }

        public Credits getCreditsObject() {
            return this.credits;
        }
    }

    public class Credits {
        public Integer min;
        public Object max;

        public String getCredits() {
            if (this.max == null){
                return this.min.toString() + "-";
            } else {
                return this.min.toString() + "-" + this.max.toString();
            }
        }
    }
}
