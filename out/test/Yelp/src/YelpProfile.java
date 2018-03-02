public class YelpProfile{
    String business_id;
    String name;
    String neighborhood;
    String address;
    String city;
    String state;
    String postal_code;
    double latitude;
    double longitude;
    int stars;
    String[] categories;
    boolean isLeast;
    final int MAX_SIZE = 10000;

    //Constructor without reviews
    public YelpProfile(String business_id, String name, String neighborhood, String address, String city, String state, String postal_code, double latitude, double longitude, int stars, String[] categories) {
        this.business_id = business_id;
        this.name = name;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.postal_code = postal_code;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stars = stars;
        this.categories = categories;
        isLeast = false;
    }


    public void display() {
        System.out.println("business_id: " + business_id);
        System.out.println("name: " + name);
        System.out.println("neighborhood: " + neighborhood);
        System.out.println("address: " + address);
        System.out.println("city: " + city);
        System.out.println("state: " + state);
        System.out.println("latitude: " + latitude);
        System.out.println("longitude: " + longitude);
        System.out.println("stars: " + stars);
        System.out.print("categories: ");
        for (int i = 0; i < categories.length; i++) {
            System.out.println(categories[i]);
        }

    }

    public double getSimilarity(YelpProfile b2) {
        if(this == b2) return 0;
        if(b2.categories == null) return 0;
        int numSameCats = 0;

        int starDiff = (Math.abs(this.stars - b2.stars)) + 1;
        for (int i = 0; i < this.categories.length; i++) {
            for (int j = 0; j < b2.categories.length; j++) {
                if (this.categories[i].equalsIgnoreCase(b2.categories[j]))
                    numSameCats++;
            }
        }
        if(numSameCats < 3) return 0;
        double man = Math.sqrt((Math.pow((this.latitude- b2.latitude),2)) +
                (Math.pow((this.longitude- b2.longitude),2)));

        return (man * 8) * (numSameCats * .5) * (starDiff * .5);
    }
    public double getSimilarityWithoutMan(YelpProfile b2) {
        if (this == b2) return 0;
        if (b2.categories.length == 0) return 0;
        int numSameCats = 0;

        int starDiff = (Math.abs(this.stars - b2.stars)) + 1;
        for (int i = 0; i < this.categories.length; i++) {
            for (int j = 0; j < b2.categories.length; j++) {
                if (this.categories[i].equalsIgnoreCase(b2.categories[j]))
                    numSameCats++;
            }
        }
        if (numSameCats < 4) return 0;

        return (numSameCats * .5) * (starDiff * .5);
    }

    public YelpProfile[] getSimilarBusinesses(YelpProfile[] profiles) {
        YelpProfile[] similars = new YelpProfile[10];
        for (int i = 0; i < 10; i++) {
            similars[i] = profiles[i];
        }
        int numZeroSims = 0;
        int least = findLeast(similars);
        for (int i = 10; i < MAX_SIZE; i++) {
            if (this.getSimilarity(profiles[i]) > this.getSimilarity(similars[least])) {
                similars[least] = profiles[i];
                for(int j = 0; j < 10; j ++) {
                    least = findLeast(similars);
                    if (this.getSimilarity(similars[j]) == 0)
                        numZeroSims++;
                }
            }
        }
        if(numZeroSims == 10) {
            System.out.println("-----------No similars------------");
            return getSimilarBusinessesWithoutMan(profiles);
        }

        return similars;
    }
    public YelpProfile[] getSimilarBusinessesWithoutMan(YelpProfile[] profiles) {
        YelpProfile[] similars = new YelpProfile[10];
        for (int i = 0; i < 10; i++) {
            similars[i] = profiles[i];
        }
        int least = findLeastWithoutMan(similars);
        for (int i = 10; i < MAX_SIZE; i++) {
            if (this.getSimilarityWithoutMan(profiles[i]) > this.getSimilarityWithoutMan(similars[least])) {
                similars[least] = profiles[i];
                for(int j = 0; j < 10; j ++) {
                    least = findLeastWithoutMan(similars);
                }
            }
        }

        return similars;
    }

    public int findLeast(YelpProfile[] ary) {
        YelpProfile leastSimilar = ary[0];
        int li = 0;
        for (int i = 0; i < ary.length; i++) {
            if (this.getSimilarity(leastSimilar) > this.getSimilarity(ary[i])) {
                li = i;
            }
        }
        return li;
    }
    public int findLeastWithoutMan(YelpProfile[] ary) {
        YelpProfile leastSimilar = ary[0];
        int li = 0;
        for (int i = 0; i < ary.length; i++) {
            if (this.getSimilarityWithoutMan(ary[i]) > this.getSimilarityWithoutMan(ary[i])) {
                li = i;
            }
        }
        return li;
    }

    @Override
    public String toString(){
        return "NAME: " + this.name + " CITY: " + this.city + " STARS: " + this.stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
