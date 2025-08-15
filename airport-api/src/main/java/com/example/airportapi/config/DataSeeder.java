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
     * This seeder populates the database with comprehensive Canadian airport data for development
     * and demo purposes, featuring major Canadian airlines, airports, and realistic flight schedules.
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

            // Force reseeding for demo - clear existing data and populate comprehensive Canadian data
            if (airportRepo.count() > 0) {
                System.out.println("🔄 Existing data found. Clearing for comprehensive Canadian airport reseed...");
                flightRepo.deleteAll();
                gateRepo.deleteAll();
                aircraftRepo.deleteAll();
                airlineRepo.deleteAll();
                airportRepo.deleteAll();
                cityRepo.deleteAll();
                System.out.println("✅ Database cleared. Proceeding with comprehensive Canadian aviation data seeding...");
            }

            System.out.println("🍁 Starting Canadian Aviation Database Seeding...");

            // ===== CANADIAN CITIES =====
            City toronto = new City("Toronto", "Ontario", 3000000);
            City vancouver = new City("Vancouver", "British Columbia", 2500000);
            City montreal = new City("Montreal", "Quebec", 1700000);
            City calgary = new City("Calgary", "Alberta", 1400000);
            City ottawa = new City("Ottawa", "Ontario", 1000000);
            City edmonton = new City("Edmonton", "Alberta", 1000000);
            City winnipeg = new City("Winnipeg", "Manitoba", 750000);
            City halifax = new City("Halifax", "Nova Scotia", 450000);
            
            // Key International Destinations for Canadian Routes
            City newYork = new City("New York", "New York", 8400000);
            City losAngeles = new City("Los Angeles", "California", 4000000);
            City chicago = new City("Chicago", "Illinois", 2700000);
            City london = new City("London", "England", 9000000);
            City paris = new City("Paris", "France", 2200000);
            City frankfurt = new City("Frankfurt", "Germany", 750000);
            City tokyo = new City("Tokyo", "Japan", 14000000);
            City singapore = new City("Singapore", "Singapore", 5900000);

            cityRepo.saveAll(Arrays.asList(
                // Canadian Cities
                toronto, vancouver, montreal, calgary, ottawa, edmonton, winnipeg, halifax,
                // International Destinations
                newYork, losAngeles, chicago, london, paris, frankfurt, tokyo, singapore
            ));

            // ===== CANADIAN AIRPORTS =====
            Airport yyz = new Airport("Toronto Pearson International", "YYZ", toronto);
            Airport yvr = new Airport("Vancouver International", "YVR", vancouver);
            Airport yul = new Airport("Montreal-Pierre Elliott Trudeau International", "YUL", montreal);
            Airport yyc = new Airport("Calgary International", "YYC", calgary);
            Airport yow = new Airport("Ottawa Macdonald-Cartier International", "YOW", ottawa);
            Airport yeg = new Airport("Edmonton International", "YEG", edmonton);
            Airport ywg = new Airport("Winnipeg Richardson International", "YWG", winnipeg);
            Airport yhz = new Airport("Halifax Stanfield International", "YHZ", halifax);
            
            // International Airports for Canadian Routes
            Airport jfk = new Airport("John F. Kennedy International", "JFK", newYork);
            Airport lax = new Airport("Los Angeles International", "LAX", losAngeles);
            Airport ord = new Airport("O'Hare International", "ORD", chicago);
            Airport lhr = new Airport("London Heathrow", "LHR", london);
            Airport cdg = new Airport("Charles de Gaulle", "CDG", paris);
            Airport fra = new Airport("Frankfurt Airport", "FRA", frankfurt);
            Airport nrt = new Airport("Narita International", "NRT", tokyo);
            Airport sin = new Airport("Singapore Changi", "SIN", singapore);

            airportRepo.saveAll(Arrays.asList(
                // Canadian Airports
                yyz, yvr, yul, yyc, yow, yeg, ywg, yhz,
                // International Airports
                jfk, lax, ord, lhr, cdg, fra, nrt, sin
            ));

            // ===== AIRLINES =====
            List<Airline> airlines = Arrays.asList(
                // Canadian Airlines
                new Airline("Air Canada", "AC"),
                new Airline("WestJet", "WS"),
                new Airline("Porter Airlines", "PD"),
                new Airline("Air Transat", "TS"),
                new Airline("Flair Airlines", "F8"),
                
                // Major International Airlines
                new Airline("American Airlines", "AA"),
                new Airline("Delta Air Lines", "DL"),
                new Airline("United Airlines", "UA"),
                new Airline("British Airways", "BA"),
                new Airline("Air France", "AF"),
                new Airline("Lufthansa", "LH"),
                new Airline("Japan Airlines", "JL"),
                new Airline("Singapore Airlines", "SQ")
            );
            airlineRepo.saveAll(airlines);

            // ===== AIRCRAFT FLEET =====
            List<Aircraft> aircraft = Arrays.asList(
                // Air Canada Fleet
                new Aircraft("Boeing 777-300ER", "Air Canada", 400),
                new Aircraft("Boeing 787-9", "Air Canada", 298),
                new Aircraft("Airbus A330-300", "Air Canada", 297),
                new Aircraft("Boeing 737 MAX 8", "Air Canada", 169),
                new Aircraft("Embraer E190", "Air Canada", 97),
                
                // WestJet Fleet
                new Aircraft("Boeing 737-800", "WestJet", 174),
                new Aircraft("Boeing 737 MAX 8", "WestJet", 189),
                new Aircraft("Boeing 787-9", "WestJet", 320),
                
                // Porter Airlines Fleet
                new Aircraft("Embraer E195-E2", "Porter Airlines", 132),
                new Aircraft("De Havilland Dash 8-400", "Porter Airlines", 78),
                
                // Air Transat Fleet
                new Aircraft("Airbus A330-200", "Air Transat", 332),
                new Aircraft("Airbus A321neo", "Air Transat", 199),
                
                // International Airlines Aircraft
                new Aircraft("Boeing 777-300ER", "American Airlines", 310),
                new Aircraft("Boeing 737-800", "American Airlines", 172),
                new Aircraft("Airbus A350-900", "Delta Air Lines", 306),
                new Aircraft("Boeing 767-300ER", "Delta Air Lines", 218),
                new Aircraft("Boeing 777-200ER", "United Airlines", 276),
                new Aircraft("Boeing 737-800", "United Airlines", 166),
                new Aircraft("Airbus A380-800", "British Airways", 469),
                new Aircraft("Boeing 777-300ER", "British Airways", 297),
                new Aircraft("Airbus A350-900", "Air France", 324),
                new Aircraft("Boeing 777-300ER", "Air France", 381),
                new Aircraft("Airbus A350-900", "Lufthansa", 293),
                new Aircraft("Boeing 747-8", "Lufthansa", 364),
                new Aircraft("Boeing 777-300ER", "Japan Airlines", 244),
                new Aircraft("Boeing 787-9", "Japan Airlines", 203),
                new Aircraft("Airbus A380-800", "Singapore Airlines", 471),
                new Aircraft("Boeing 777-300ER", "Singapore Airlines", 264)
            );
            aircraftRepo.saveAll(aircraft);

            // ===== AIRPORT GATES =====
            List<Gate> gates = Arrays.asList(
                // YYZ - Toronto Pearson (Terminal 1: A, B, C gates; Terminal 3: D, E, F gates)
                new Gate("A1", yyz), new Gate("A5", yyz), new Gate("A12", yyz), new Gate("A18", yyz),
                new Gate("B2", yyz), new Gate("B8", yyz), new Gate("B15", yyz), new Gate("B22", yyz),
                new Gate("C3", yyz), new Gate("C10", yyz), new Gate("C16", yyz), new Gate("C25", yyz),
                new Gate("D4", yyz), new Gate("D11", yyz), new Gate("D20", yyz), new Gate("D28", yyz),
                new Gate("E6", yyz), new Gate("E14", yyz), new Gate("E23", yyz), new Gate("E30", yyz),
                new Gate("F7", yyz), new Gate("F16", yyz), new Gate("F24", yyz), new Gate("F32", yyz),
                
                // YVR - Vancouver International (Main Terminal, International Terminal)
                new Gate("A2", yvr), new Gate("A8", yvr), new Gate("A14", yvr), new Gate("A20", yvr),
                new Gate("B3", yvr), new Gate("B9", yvr), new Gate("B17", yvr), new Gate("B25", yvr),
                new Gate("C4", yvr), new Gate("C12", yvr), new Gate("C19", yvr), new Gate("C26", yvr),
                new Gate("D5", yvr), new Gate("D13", yvr), new Gate("D21", yvr), new Gate("D29", yvr),
                
                // YUL - Montreal (Terminal A, Terminal B)
                new Gate("A6", yul), new Gate("A15", yul), new Gate("A22", yul), new Gate("A28", yul),
                new Gate("B7", yul), new Gate("B16", yul), new Gate("B24", yul), new Gate("B31", yul),
                
                // YYC - Calgary (Domestic Terminal, International Terminal)
                new Gate("A9", yyc), new Gate("A17", yyc), new Gate("A26", yyc), new Gate("A33", yyc),
                new Gate("B10", yyc), new Gate("B18", yyc), new Gate("B27", yyc), new Gate("B35", yyc),
                
                // Other Canadian Airports
                new Gate("A11", yow), new Gate("B12", yow), new Gate("C13", yow), new Gate("D14", yow),
                new Gate("A15", yeg), new Gate("B16", yeg), new Gate("C17", yeg), new Gate("D18", yeg),
                new Gate("A19", ywg), new Gate("B20", ywg), new Gate("C21", ywg), new Gate("D22", ywg),
                new Gate("A23", yhz), new Gate("B24", yhz), new Gate("C25", yhz), new Gate("D26", yhz),
                
                // International Airport Gates
                new Gate("1", jfk), new Gate("2", jfk), new Gate("4A", jfk), new Gate("5B", jfk),
                new Gate("3", lax), new Gate("5", lax), new Gate("7A", lax), new Gate("9B", lax),
                new Gate("B1", ord), new Gate("B5", ord), new Gate("C3", ord), new Gate("C7", ord),
                new Gate("A1", lhr), new Gate("A3", lhr), new Gate("B2", lhr), new Gate("B4", lhr),
                new Gate("E1", cdg), new Gate("E3", cdg), new Gate("F2", cdg), new Gate("F4", cdg),
                new Gate("A1", fra), new Gate("A3", fra), new Gate("B2", fra), new Gate("B4", fra),
                new Gate("S1", nrt), new Gate("S3", nrt), new Gate("N2", nrt), new Gate("N4", nrt),
                new Gate("A1", sin), new Gate("A3", sin), new Gate("B2", sin), new Gate("B4", sin)
            );
            gateRepo.saveAll(gates);

            // ===== COMPREHENSIVE FLIGHT SCHEDULES =====
            LocalDateTime now = LocalDateTime.now();
            List<FlightSchedule> flights = Arrays.asList(
                // ===== AIR CANADA FLIGHTS =====
                createFlightWithDetails("AC101", FlightType.DEPARTURE, FlightStatus.ON_TIME,
                    now.plusHours(2), airlines.get(0), aircraft.get(0), gates.get(0), yyz, yvr,
                    "Boeing 777-300ER", "A1", "1", 400),
                    
                createFlightWithDetails("AC102", FlightType.ARRIVAL, FlightStatus.BOARDING,
                    now.plusHours(5), airlines.get(0), aircraft.get(1), gates.get(4), yvr, yyz,
                    "Boeing 787-9", "B2", "1", 298),
                    
                createFlightWithDetails("AC201", FlightType.DEPARTURE, FlightStatus.CHECKING_IN,
                    now.plusHours(1), airlines.get(0), aircraft.get(2), gates.get(8), yyz, yul,
                    "Airbus A330-300", "C3", "1", 297),
                    
                createFlightWithDetails("AC202", FlightType.ARRIVAL, FlightStatus.ON_TIME,
                    now.plusMinutes(30), airlines.get(0), aircraft.get(3), gates.get(32), yul, yyz,
                    "Boeing 737 MAX 8", "A6", "A", 169),
                    
                createFlightWithDetails("AC301", FlightType.DEPARTURE, FlightStatus.DELAYED,
                    now.plusHours(3), airlines.get(0), aircraft.get(0), gates.get(12), yyz, yyc,
                    "Boeing 777-300ER", "C10", "1", 400),
                    
                createFlightWithDetails("AC8801", FlightType.DEPARTURE, FlightStatus.ON_TIME,
                    now.plusHours(8), airlines.get(0), aircraft.get(0), gates.get(16), yyz, lhr,
                    "Boeing 777-300ER", "D4", "3", 400),
                    
                createFlightWithDetails("AC7701", FlightType.DEPARTURE, FlightStatus.ON_TIME,
                    now.plusHours(6), airlines.get(0), aircraft.get(1), gates.get(20), yyz, fra,
                    "Boeing 787-9", "E6", "3", 298),
                    
                // ===== WESTJET FLIGHTS =====
                createFlightWithDetails("WS501", FlightType.DEPARTURE, FlightStatus.ON_TIME,
                    now.plusHours(4), airlines.get(1), aircraft.get(5), gates.get(24), yvr, yyc,
                    "Boeing 737-800", "A2", "Domestic", 174),
                    
                createFlightWithDetails("WS502", FlightType.ARRIVAL, FlightStatus.ARRIVED,
                    now.minusHours(1), airlines.get(1), aircraft.get(6), gates.get(40), yyc, yvr,
                    "Boeing 737 MAX 8", "A9", "Domestic", 189),
                    
                createFlightWithDetails("WS1001", FlightType.DEPARTURE, FlightStatus.BOARDING,
                    now.plusHours(7), airlines.get(1), aircraft.get(7), gates.get(28), yvr, lax,
                    "Boeing 787-9", "C4", "International", 320),
                    
                // ===== PORTER AIRLINES FLIGHTS =====
                createFlightWithDetails("PD801", FlightType.DEPARTURE, FlightStatus.ON_TIME,
                    now.plusHours(2), airlines.get(2), aircraft.get(8), gates.get(44), yow, yyz,
                    "Embraer E195-E2", "A11", "Main", 132),
                    
                createFlightWithDetails("PD802", FlightType.ARRIVAL, FlightStatus.ON_TIME,
                    now.plusMinutes(45), airlines.get(2), aircraft.get(9), gates.get(2), yyz, yow,
                    "De Havilland Dash 8-400", "A5", "1", 78),
                    
                // ===== AIR TRANSAT FLIGHTS =====
                createFlightWithDetails("TS401", FlightType.DEPARTURE, FlightStatus.CHECKING_IN,
                    now.plusHours(5), airlines.get(3), aircraft.get(10), gates.get(34), yul, cdg,
                    "Airbus A330-200", "A15", "A", 332),
                    
                createFlightWithDetails("TS501", FlightType.DEPARTURE, FlightStatus.ON_TIME,
                    now.plusHours(9), airlines.get(3), aircraft.get(11), gates.get(6), yyz, cdg,
                    "Airbus A321neo", "B8", "1", 199),
                    
                // ===== INTERNATIONAL ARRIVALS TO CANADA =====
                createFlightWithDetails("AA1234", FlightType.ARRIVAL, FlightStatus.DELAYED,
                    now.plusHours(2), airlines.get(5), aircraft.get(12), gates.get(18), jfk, yyz,
                    "Boeing 777-300ER", "E14", "3", 310),
                    
                createFlightWithDetails("DL2345", FlightType.ARRIVAL, FlightStatus.ON_TIME,
                    now.plusHours(3), airlines.get(6), aircraft.get(14), gates.get(30), lax, yvr,
                    "Airbus A350-900", "C12", "International", 306),
                    
                createFlightWithDetails("BA9876", FlightType.ARRIVAL, FlightStatus.BOARDING,
                    now.plusHours(1), airlines.get(8), aircraft.get(16), gates.get(14), lhr, yyz,
                    "Airbus A380-800", "D11", "3", 469),
                    
                createFlightWithDetails("AF3456", FlightType.ARRIVAL, FlightStatus.GATE_CHANGE,
                    now.plusMinutes(20), airlines.get(9), aircraft.get(18), gates.get(36), cdg, yul,
                    "Airbus A350-900", "B7", "A", 324),
                    
                createFlightWithDetails("LH4567", FlightType.ARRIVAL, FlightStatus.ON_TIME,
                    now.plusHours(4), airlines.get(10), aircraft.get(20), gates.get(22), fra, yyz,
                    "Airbus A350-900", "F7", "3", 293),
                    
                createFlightWithDetails("JL5678", FlightType.ARRIVAL, FlightStatus.ARRIVED,
                    now.minusMinutes(30), airlines.get(11), aircraft.get(22), gates.get(26), nrt, yvr,
                    "Boeing 777-300ER", "B9", "International", 244),
                    
                createFlightWithDetails("SQ6789", FlightType.ARRIVAL, FlightStatus.ON_TIME,
                    now.plusHours(6), airlines.get(12), aircraft.get(24), gates.get(10), sin, yvr,
                    "Airbus A380-800", "A8", "International", 471)
            );
            
            flightRepo.saveAll(flights);

            System.out.println("=== CANADIAN AVIATION DATABASE SEEDED SUCCESSFULLY ===");
            System.out.println("🍁 " + cityRepo.count() + " cities (8 Canadian + 8 international)");
            System.out.println("✈️ " + airportRepo.count() + " airports (8 Canadian hubs + 8 international destinations)"); 
            System.out.println("🏢 " + airlineRepo.count() + " airlines (5 Canadian + 8 international)");
            System.out.println("🛩️ " + aircraftRepo.count() + " aircraft (realistic Canadian & international fleets)");
            System.out.println("🚪 " + gateRepo.count() + " gates (distributed across all terminals)");
            System.out.println("📅 " + flightRepo.count() + " flights (comprehensive Canadian routes + international connections)");
            System.out.println("\nFeatured Canadian Airports:");
            System.out.println("  • YYZ - Toronto Pearson International");
            System.out.println("  • YVR - Vancouver International");
            System.out.println("  • YUL - Montreal-Pierre Elliott Trudeau International");
            System.out.println("  • YYC - Calgary International");
            System.out.println("  • YOW - Ottawa Macdonald-Cartier International");
            System.out.println("  • YEG - Edmonton International");
            System.out.println("  • YWG - Winnipeg Richardson International");
            System.out.println("  • YHZ - Halifax Stanfield International");
        };
    }
    
    /**
     * Helper method to create a FlightSchedule with all the new direct flight information fields
     */
    private FlightSchedule createFlightWithDetails(String flightNumber, FlightType flightType, 
            FlightStatus status, LocalDateTime scheduledTime, Airline airline, Aircraft aircraft, 
            Gate gate, Airport origin, Airport destination, String aircraftType, String gateNumber, 
            String terminalNumber, Integer passengerCapacity) {
        
        FlightSchedule flight = new FlightSchedule(flightNumber, flightType, status, scheduledTime,
                airline, aircraft, gate, origin, destination);
        
        // Set the new direct flight information fields
        flight.setAircraftType(aircraftType);
        flight.setGateNumber(gateNumber);
        flight.setTerminalNumber(terminalNumber);
        flight.setPassengerCapacity(passengerCapacity);
        
        return flight;
    }
}
