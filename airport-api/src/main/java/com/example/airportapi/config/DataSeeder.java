package com.example.airportapi.config;

import com.example.airportapi.model.*;
import com.example.airportapi.model.enums.FlightStatus;
import com.example.airportapi.model.enums.FlightType;
import com.example.airportapi.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataSeeder {

    /**
     * This seeder populates the database with comprehensive sample data for development and demo
     * purposes including major airlines, airports worldwide, and various aircraft types.
     * It only runs if no airports exist to avoid duplication.
     * Excluded from test profile to prevent conflicts with test data.
     */
    @Bean
    @Profile("!test")
    public CommandLineRunner seedData(
            CityRepository cityRepo,
            AirportRepository airportRepo,
            GateRepository gateRepo,
            AircraftRepository aircraftRepo,
            AirlineRepository airlineRepo,
            FlightScheduleRepository flightRepo) {
        return args -> {

            if (airportRepo.count() > 0) {
                System.out.println("Database already seeded. Adding missing airlines and airports...");
                
                // Add missing airlines
                if (airlineRepo.findByName("American Airlines").isEmpty()) {
                    List<Airline> newAirlines = Arrays.asList(
                        new Airline("American Airlines", "AA"),
                        new Airline("Delta Air Lines", "DL"),
                        new Airline("United Airlines", "UA"),
                        new Airline("Southwest Airlines", "WN"),
                        new Airline("JetBlue Airways", "B6"),
                        new Airline("British Airways", "BA"),
                        new Airline("Lufthansa", "LH"),
                        new Airline("Air France", "AF"),
                        new Airline("Singapore Airlines", "SQ"),
                        new Airline("Cathay Pacific", "CX")
                    );
                    airlineRepo.saveAll(newAirlines);
                    System.out.println("Added " + newAirlines.size() + " new airlines");
                }
                
                // Add missing airports and cities  
                if (airportRepo.findByCode("LAX").isEmpty()) {
                    // Cities
                    City losAngeles = new City("Los Angeles", "California", 4000000);
                    City newYork = new City("New York", "New York", 8400000);
                    City london = new City("London", "England", 9000000);
                    City singapore = new City("Singapore", "Singapore", 5900000);
                    cityRepo.saveAll(Arrays.asList(losAngeles, newYork, london, singapore));
                    
                    // Airports
                    List<Airport> newAirports = Arrays.asList(
                        new Airport("Los Angeles International", "LAX", losAngeles),
                        new Airport("John F. Kennedy International", "JFK", newYork),
                        new Airport("London Heathrow", "LHR", london),
                        new Airport("Singapore Changi", "SIN", singapore)
                    );
                    airportRepo.saveAll(newAirports);
                    System.out.println("Added " + newAirports.size() + " new airports");
                    
                    // Add gates for new airports
                    List<Gate> newGates = Arrays.asList(
                        new Gate("1", newAirports.get(0)), new Gate("2", newAirports.get(0)),
                        new Gate("1A", newAirports.get(1)), new Gate("1B", newAirports.get(1)),
                        new Gate("A1", newAirports.get(2)), new Gate("A2", newAirports.get(2)),
                        new Gate("A1", newAirports.get(3)), new Gate("A2", newAirports.get(3))
                    );
                    gateRepo.saveAll(newGates);
                    
                    // Add aircraft for new airlines
                    List<Aircraft> newAircraft = Arrays.asList(
                        new Aircraft("Boeing 737-800", "American Airlines", 172),
                        new Aircraft("Boeing 777-300ER", "American Airlines", 310),
                        new Aircraft("Boeing 737-900ER", "Delta Air Lines", 180),
                        new Aircraft("Airbus A350-900", "Delta Air Lines", 306),
                        new Aircraft("Boeing 737-800", "United Airlines", 166),
                        new Aircraft("Boeing 777-300ER", "British Airways", 297),
                        new Aircraft("Airbus A350-900", "Lufthansa", 293),
                        new Aircraft("Airbus A380-800", "Singapore Airlines", 471)
                    );
                    aircraftRepo.saveAll(newAircraft);
                    System.out.println("Added " + newAircraft.size() + " new aircraft");
                }
                
                System.out.println("Comprehensive data update completed!");
                return;
            }

            // ===== CITIES =====
            // North America
            City toronto = new City("Toronto", "Ontario", 3000000);
            City vancouver = new City("Vancouver", "British Columbia", 2500000);
            City montreal = new City("Montreal", "Quebec", 1700000);
            City calgary = new City("Calgary", "Alberta", 1400000);
            City newYork = new City("New York", "New York", 8400000);
            City losAngeles = new City("Los Angeles", "California", 4000000);
            City chicago = new City("Chicago", "Illinois", 2700000);
            City miami = new City("Miami", "Florida", 470000);
            City sanFrancisco = new City("San Francisco", "California", 880000);
            City seattle = new City("Seattle", "Washington", 750000);
            City denver = new City("Denver", "Colorado", 715000);
            City atlanta = new City("Atlanta", "Georgia", 500000);
            
            // Europe
            City london = new City("London", "England", 9000000);
            City paris = new City("Paris", "France", 2200000);
            City amsterdam = new City("Amsterdam", "Netherlands", 870000);
            City frankfurt = new City("Frankfurt", "Germany", 750000);
            City madrid = new City("Madrid", "Spain", 3200000);
            City rome = new City("Rome", "Italy", 2800000);
            City zurich = new City("Zurich", "Switzerland", 430000);
            
            // Asia-Pacific
            City tokyo = new City("Tokyo", "Japan", 14000000);
            City singapore = new City("Singapore", "Singapore", 5900000);
            City hongKong = new City("Hong Kong", "Hong Kong", 7500000);
            City sydney = new City("Sydney", "Australia", 5300000);
            City melbourne = new City("Melbourne", "Australia", 5100000);

            cityRepo.saveAll(Arrays.asList(
                // North America
                toronto, vancouver, montreal, calgary, newYork, losAngeles, chicago, 
                miami, sanFrancisco, seattle, denver, atlanta,
                // Europe  
                london, paris, amsterdam, frankfurt, madrid, rome, zurich,
                // Asia-Pacific
                tokyo, singapore, hongKong, sydney, melbourne
            ));

            // ===== AIRPORTS =====
            // North America
            Airport yyz = new Airport("Toronto Pearson International", "YYZ", toronto);
            Airport yvr = new Airport("Vancouver International", "YVR", vancouver);
            Airport yul = new Airport("Montreal-Pierre Elliott Trudeau International", "YUL", montreal);
            Airport yyc = new Airport("Calgary International", "YYC", calgary);
            Airport jfk = new Airport("John F. Kennedy International", "JFK", newYork);
            Airport lga = new Airport("LaGuardia Airport", "LGA", newYork);
            Airport lax = new Airport("Los Angeles International", "LAX", losAngeles);
            Airport ord = new Airport("O'Hare International", "ORD", chicago);
            Airport mia = new Airport("Miami International", "MIA", miami);
            Airport sfo = new Airport("San Francisco International", "SFO", sanFrancisco);
            Airport sea = new Airport("Seattle-Tacoma International", "SEA", seattle);
            Airport den = new Airport("Denver International", "DEN", denver);
            Airport atl = new Airport("Hartsfield-Jackson Atlanta International", "ATL", atlanta);
            
            // Europe
            Airport lhr = new Airport("London Heathrow", "LHR", london);
            Airport lgw = new Airport("London Gatwick", "LGW", london);
            Airport cdg = new Airport("Charles de Gaulle", "CDG", paris);
            Airport ams = new Airport("Amsterdam Schiphol", "AMS", amsterdam);
            Airport fra = new Airport("Frankfurt am Main", "FRA", frankfurt);
            Airport mad = new Airport("Adolfo Suárez Madrid-Barajas", "MAD", madrid);
            Airport fco = new Airport("Leonardo da Vinci-Fiumicino", "FCO", rome);
            Airport zur = new Airport("Zurich Airport", "ZUR", zurich);
            
            // Asia-Pacific
            Airport nrt = new Airport("Narita International", "NRT", tokyo);
            Airport hnd = new Airport("Haneda Airport", "HND", tokyo);
            Airport sin = new Airport("Singapore Changi", "SIN", singapore);
            Airport hkg = new Airport("Hong Kong International", "HKG", hongKong);
            Airport syd = new Airport("Sydney Kingsford Smith", "SYD", sydney);
            Airport mel = new Airport("Melbourne Airport", "MEL", melbourne);

            airportRepo.saveAll(Arrays.asList(
                // North America
                yyz, yvr, yul, yyc, jfk, lga, lax, ord, mia, sfo, sea, den, atl,
                // Europe
                lhr, lgw, cdg, ams, fra, mad, fco, zur,
                // Asia-Pacific
                nrt, hnd, sin, hkg, syd, mel
            ));

            // ===== GATES =====
            // Create gates for major airports
            List<Gate> gates = Arrays.asList(
                // YYZ Gates
                new Gate("A1", yyz), new Gate("A2", yyz), new Gate("A3", yyz), new Gate("A4", yyz),
                new Gate("B1", yyz), new Gate("B2", yyz), new Gate("B3", yyz), new Gate("B4", yyz),
                new Gate("C1", yyz), new Gate("C2", yyz), new Gate("C3", yyz), new Gate("C4", yyz),
                
                // YVR Gates
                new Gate("A1", yvr), new Gate("A2", yvr), new Gate("B1", yvr), new Gate("B2", yvr),
                new Gate("C1", yvr), new Gate("C2", yvr), new Gate("D1", yvr), new Gate("D2", yvr),
                
                // LAX Gates
                new Gate("1", lax), new Gate("2", lax), new Gate("3", lax), new Gate("4", lax),
                new Gate("5", lax), new Gate("6", lax), new Gate("7", lax), new Gate("8", lax),
                
                // JFK Gates
                new Gate("1A", jfk), new Gate("1B", jfk), new Gate("2A", jfk), new Gate("2B", jfk),
                new Gate("4A", jfk), new Gate("4B", jfk), new Gate("5A", jfk), new Gate("5B", jfk),
                
                // LHR Gates
                new Gate("A1", lhr), new Gate("A2", lhr), new Gate("B1", lhr), new Gate("B2", lhr),
                new Gate("C1", lhr), new Gate("C2", lhr), new Gate("D1", lhr), new Gate("D2", lhr),
                
                // Additional major airport gates
                new Gate("A1", ord), new Gate("A2", ord), new Gate("B1", ord), new Gate("B2", ord),
                new Gate("A1", atl), new Gate("A2", atl), new Gate("B1", atl), new Gate("B2", atl),
                new Gate("A1", den), new Gate("A2", den), new Gate("B1", den), new Gate("B2", den),
                new Gate("A1", sfo), new Gate("A2", sfo), new Gate("B1", sfo), new Gate("B2", sfo),
                new Gate("A1", sea), new Gate("A2", sea), new Gate("B1", sea), new Gate("B2", sea)
            );
            gateRepo.saveAll(gates);

            // ===== AIRLINES =====
            List<Airline> airlines = Arrays.asList(
                // North American Airlines
                new Airline("Air Canada", "AC"),
                new Airline("WestJet", "WS"),
                new Airline("American Airlines", "AA"),
                new Airline("Delta Air Lines", "DL"),
                new Airline("United Airlines", "UA"),
                new Airline("Southwest Airlines", "WN"),
                new Airline("JetBlue Airways", "B6"),
                new Airline("Alaska Airlines", "AS"),
                new Airline("Spirit Airlines", "NK"),
                new Airline("Frontier Airlines", "F9"),
                
                // European Airlines
                new Airline("British Airways", "BA"),
                new Airline("Lufthansa", "LH"),
                new Airline("Air France", "AF"),
                new Airline("KLM Royal Dutch Airlines", "KL"),
                new Airline("Swiss International Air Lines", "LX"),
                new Airline("Iberia", "IB"),
                new Airline("Alitalia", "AZ"),
                new Airline("Ryanair", "FR"),
                new Airline("EasyJet", "U2"),
                new Airline("Virgin Atlantic", "VS"),
                
                // Asia-Pacific Airlines  
                new Airline("Japan Airlines", "JL"),
                new Airline("All Nippon Airways", "NH"),
                new Airline("Singapore Airlines", "SQ"),
                new Airline("Cathay Pacific", "CX"),
                new Airline("Qantas Airways", "QF"),
                new Airline("Jetstar Airways", "JQ"),
                new Airline("Virgin Australia", "VA"),
                new Airline("Korean Air", "KE"),
                new Airline("Asiana Airlines", "OZ"),
                new Airline("Thai Airways", "TG")
            );
            airlineRepo.saveAll(airlines);

            // ===== AIRCRAFT =====
            List<Aircraft> aircraft = Arrays.asList(
                // Air Canada Fleet
                new Aircraft("Boeing 737-800", "Air Canada", 160),
                new Aircraft("Boeing 777-300ER", "Air Canada", 400),
                new Aircraft("Airbus A320", "Air Canada", 146),
                new Aircraft("Airbus A330-300", "Air Canada", 297),
                new Aircraft("Boeing 787-9", "Air Canada", 298),
                
                // WestJet Fleet
                new Aircraft("Boeing 737-700", "WestJet", 136),
                new Aircraft("Boeing 737-800", "WestJet", 166),
                new Aircraft("Boeing 737 MAX 8", "WestJet", 174),
                new Aircraft("Boeing 787-9", "WestJet", 320),
                
                // American Airlines Fleet
                new Aircraft("Boeing 737-800", "American Airlines", 172),
                new Aircraft("Boeing 777-300ER", "American Airlines", 310),
                new Aircraft("Airbus A321", "American Airlines", 187),
                new Aircraft("Boeing 787-8", "American Airlines", 234),
                new Aircraft("Airbus A330-300", "American Airlines", 287),
                
                // Delta Air Lines Fleet
                new Aircraft("Boeing 737-900ER", "Delta Air Lines", 180),
                new Aircraft("Airbus A350-900", "Delta Air Lines", 306),
                new Aircraft("Boeing 767-300ER", "Delta Air Lines", 261),
                new Aircraft("Airbus A321", "Delta Air Lines", 192),
                new Aircraft("Boeing 757-200", "Delta Air Lines", 199),
                
                // United Airlines Fleet
                new Aircraft("Boeing 737-800", "United Airlines", 166),
                new Aircraft("Boeing 777-300ER", "United Airlines", 366),
                new Aircraft("Boeing 787-10", "United Airlines", 318),
                new Aircraft("Airbus A320", "United Airlines", 138),
                new Aircraft("Boeing 767-300ER", "United Airlines", 214),
                
                // British Airways Fleet
                new Aircraft("Boeing 777-300ER", "British Airways", 297),
                new Aircraft("Airbus A380-800", "British Airways", 469),
                new Aircraft("Boeing 787-9", "British Airways", 216),
                new Aircraft("Airbus A350-1000", "British Airways", 331),
                new Aircraft("Airbus A320", "British Airways", 132),
                
                // Lufthansa Fleet
                new Aircraft("Airbus A350-900", "Lufthansa", 293),
                new Aircraft("Boeing 747-8", "Lufthansa", 364),
                new Aircraft("Airbus A380-800", "Lufthansa", 509),
                new Aircraft("Boeing 787-9", "Lufthansa", 294),
                new Aircraft("Airbus A321", "Lufthansa", 200),
                
                // Singapore Airlines Fleet
                new Aircraft("Airbus A380-800", "Singapore Airlines", 471),
                new Aircraft("Boeing 777-300ER", "Singapore Airlines", 264),
                new Aircraft("Airbus A350-900", "Singapore Airlines", 253),
                new Aircraft("Boeing 787-10", "Singapore Airlines", 337),
                new Aircraft("Airbus A330-300", "Singapore Airlines", 285)
            );
            aircraftRepo.saveAll(aircraft);

            // ===== SAMPLE FLIGHTS =====
            LocalDateTime now = LocalDateTime.now();
            List<FlightSchedule> flights = Arrays.asList(
                // Air Canada flights
                new FlightSchedule("AC123", FlightType.DEPARTURE, FlightStatus.ON_TIME, 
                    now.plusHours(2), airlines.get(0), aircraft.get(0), gates.get(0), yyz, yvr),
                new FlightSchedule("AC456", FlightType.ARRIVAL, FlightStatus.BOARDING, 
                    now.plusHours(1), airlines.get(0), aircraft.get(1), gates.get(1), yvr, yyz),
                
                // American Airlines flights  
                new FlightSchedule("AA100", FlightType.DEPARTURE, FlightStatus.DELAYED, 
                    now.plusHours(3), airlines.get(2), aircraft.get(10), gates.get(16), lax, jfk),
                new FlightSchedule("AA200", FlightType.ARRIVAL, FlightStatus.ARRIVED, 
                    now.minusHours(1), airlines.get(2), aircraft.get(11), gates.get(17), jfk, lax),
                
                // Delta flights
                new FlightSchedule("DL500", FlightType.DEPARTURE, FlightStatus.CHECKING_IN, 
                    now.plusHours(4), airlines.get(3), aircraft.get(15), gates.get(20), atl, lhr),
                new FlightSchedule("DL600", FlightType.ARRIVAL, FlightStatus.GATE_CHANGE, 
                    now.plusHours(2), airlines.get(3), aircraft.get(16), gates.get(21), lhr, atl),
                
                // British Airways flights
                new FlightSchedule("BA100", FlightType.DEPARTURE, FlightStatus.ON_TIME, 
                    now.plusHours(5), airlines.get(10), aircraft.get(25), gates.get(32), lhr, jfk),
                new FlightSchedule("BA200", FlightType.ARRIVAL, FlightStatus.DEPARTED, 
                    now.plusHours(8), airlines.get(10), aircraft.get(26), gates.get(33), jfk, lhr),
                
                // Singapore Airlines flights
                new FlightSchedule("SQ100", FlightType.DEPARTURE, FlightStatus.ON_TIME, 
                    now.plusHours(6), airlines.get(22), aircraft.get(40), gates.get(35), sin, lax),
                new FlightSchedule("SQ200", FlightType.ARRIVAL, FlightStatus.CANCELLED, 
                    now.plusHours(12), airlines.get(22), aircraft.get(41), gates.get(36), lax, sin)
            );
            
            flightRepo.saveAll(flights);

            System.out.println("Comprehensive seed data loaded successfully!");
            System.out.println("- " + cityRepo.count() + " cities");
            System.out.println("- " + airportRepo.count() + " airports"); 
            System.out.println("- " + airlineRepo.count() + " airlines");
            System.out.println("- " + aircraftRepo.count() + " aircraft");
            System.out.println("- " + gateRepo.count() + " gates");
            System.out.println("- " + flightRepo.count() + " sample flights");
        };
    }
}
