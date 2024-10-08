package model;
import java.util.List;

public class Cars {
    private List<Car> cars;

    public List<Car> getCars() {
        return cars;
    }

    public static class Car {
        private String brand;
        private List<Model> models;

        public String getBrand() {
            return brand;
        }

        public List<Model> getModels() {
            return models;
        }

        public static class Model {
            private String name;
            private Details details;

            public String getName() {
                return name;
            }

            public Details getDetails() {
                return details;
            }
        }

        public static class Details {
            private int year;
            private String engine;
            private int horsepower;
            private String transmission;
            private FuelEconomy fuelEconomy;
            private List<String> features;
            private List<String> availableColors;

            public int getYear() {
                return year;
            }

            public String getEngine() {
                return engine;
            }

            public int getHorsepower() {
                return horsepower;
            }

            public String getTransmission() {
                return transmission;
            }

            public FuelEconomy getFuelEconomy() {
                return fuelEconomy;
            }

            public List<String> getFeatures() {
                return features;
            }

            public List<String> getAvailableColors() {
                return availableColors;
            }
        }

        public static class FuelEconomy {
            private String city;
            private String highway;

            public String getCity() {
                return city;
            }

            public String getHighway() {
                return highway;
            }
        }
    }
}
