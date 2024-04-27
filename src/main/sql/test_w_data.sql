-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 27, 2024 at 03:41 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` text NOT NULL,
  `fname` varchar(255) NOT NULL,
  `lname` varchar(255) NOT NULL,
  `address` text NOT NULL,
  `email` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`id`, `username`, `password`, `fname`, `lname`, `address`, `email`) VALUES
(2, 'test', 'test', 'test', 'retest', 'Earth', 'email@mailto.com'),
(4, 'bruhMoment', 'bruh', 'bruh', 'moment', 'bruh city', 'bruh@gmail.com'),
(5, 'asdf', '0000', 'asdf', 'asdf', 'asdf', 'asdf'),
(6, 'RazonadoJohn', '00000000', 'John', 'Razonado', 'city', 'john.razonado@cvsu.edu.ph'),
(7, 'bruller', '0000', 'brul', 'momento', 'walk', 'bul@gmail.com'),
(8, 'blublu', '00000000', 'rebruh', 'unburh', 'asdf', 'blu@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `isBought` tinyint(1) NOT NULL,
  `billNo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`id`, `userId`, `productId`, `quantity`, `isBought`, `billNo`) VALUES
(1, 2, 37, 2, 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `category` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `stock` int(11) NOT NULL,
  `image_path` varchar(1024) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `name`, `description`, `category`, `price`, `stock`, `image_path`) VALUES
(1, 'Ishikawa Soldering Lead D0.8 60/40 250g', '0.8mm Soldering lead roll Sn60/Pb40', 'Tools', 469, 67, 'assets/products/tools/ishikwa-solderlead.png'),
(2, 'MB-102 Solderless Breadboard 830 points', 'Solderless Breadboard 830 points. Great for prototyping', 'Tools', 87, 78, 'assets/products/tools/MB102.png'),
(3, 'Desoldering pump', 'Full aluminum alloy body desoldering pump. Great for removing solder lead by suction.', 'Tools', 68, 67, 'assets/products/tools/desoldering-pump.png'),
(4, '60W Adjustable Temperature Control Soldering Iron Gray', 'oldering Iron with Built-in Temperature Controller. 60W 220V has a dial range of 200-450C (Temperature is not accurate)', 'Tools', 269, 53, 'assets/products/tools/60w-solderIron.png'),
(5, 'Quecoo T12-958 STM32 Soldering Station', 'Max output - 108w. It can have different soldering iron tip based on the T12 style tip. Grounded, fused, and ESD-Safe.', 'Tools', 2431.55, 15, 'assets/products/tools/quecoot12-958.png'),
(6, 'Plato 170 Wire Cutter', 'Shear Cutter Caution: Copper wire only. This cutter will be damaged if used to cut hard materials like iron/steel. Made from high carbon steel.', 'Tools', 66, 78, 'assets/products/tools/plato-170.png'),
(7, 'GS-01 Ground Strap', 'Ground Strap to prevent you from damaging your most expensive electronic components due to electrostatic discharge ESD.', 'Tools', 50, 65, 'assets/products/tools/GS-01.png'),
(8, 'Soldering Sponge', 'Removes and cleanse soldering iron\'s tip in one swipe.', 'Tools', 12, 80, 'assets/products/tools/soldering-sponge.png'),
(9, 'DT-830B 3-1/2 Digit Multimeter', 'Basic Functions includes ACV, DCV, DCA, Diode & Transistor Tester, Ohmmeter. Requires a 9v Battery. Probe is included', 'Tools', 144, 45, 'assets/products/tools/DT-DMM.png'),
(10, 'Tweezer Set ESD-Safe', 'Contains 6 types of tweezers. The manufacturer claims these are made of stainless steel material. But quick checks shows that, with the exception of ESD-11, all are strongly attracted to magnets.', 'Tools', 65, 66, 'assets/products/tools/tweezer-esd.png'),
(11, 'Raspberry Pi Pico', 'Raspberry Pi Pico is a low-cost, high-performance microcontroller board with flexible digital interfaces using RP2040 having dual-core Arm Cortex M0+ processor, flexible clock running up to 133 MHz. The Raspberry Pi Pico is programmed in C / C ++ and MicroPython via the microUSB connector.', 'Microcontrollers', 350, 99, 'assets/products/microcontrollers/pico.png'),
(12, 'Raspberry Pi Pico W', 'Raspberry Pi Pico W module equipped with the proprietary Raspberry RP2040 microcontroller with the CYW43439 wireless communication system. CYW43439, WiFi IEEE 802.11 b / g / n wireless communication is possible.', 'Microcontrollers', 500, 69, 'assets/products/microcontrollers/pico-w.png'),
(13, 'Arduino Uno R3', 'Uno R3 is the most popular development board among Arduino family. Based on Atmel’s ATmega328 microcontroller this board offers 14 digital input/output pins. 6 pins can be used as PWM outputs and the 6 analogue pins.', 'Microcontrollers', 449, 210, 'assets/products/microcontrollers/arduino-uno.png'),
(14, 'NodeMCU V3 ESP8266 ESP-12E', 'WiFi development board that helps you to prototype your IoT product with few Lua script lines, or through Arduino IDE. The board is based on ESP8266 ESP-12E variant, unlike other ESP-12E, you won\'t need to buy a separate breakout board.', 'Microcontrollers', 160, 105, 'assets/products/microcontrollers/ESP8266.png'),
(15, 'ESP32 WiFi IoT Development Board', 'ESP-32 Development Board with 2.4 GHz ultra-low power dual-mode Wi-Fi and Bluetooth dev kit is ideal for prototyping IoT or other devices which require wireless and internet connectivity, utilizing the powerful ESP32 microcontroller.', 'Microcontrollers', 350, 85, 'assets/products/microcontrollers/ESP32.png'),
(16, 'Arduino Pro Mini 328-5V 16MHz', 'Based on the ATmega328 . It has 14 digital input/output pins (of which 6 can be used as PWM outputs), 6 analog inputs, an on-board resonator, a reset button, and holes for mounting pin headers. You will need a USB to Serial Converter to program.', 'Microcontrollers', 205, 75, 'assets/products/microcontrollers/arduino-promini.png'),
(17, 'Arduino Mega 2560 R3', 'It features 54 digital input/output pins (of which 15 can be used as PWM outputs) 16 analog inputs 4 UARTs (hardware serial ports) a 16 MHz crystal oscillator a USB connection a power jack an ICSP header and a reset button.', 'Microcontrollers', 1149, 45, 'assets/products/microcontrollers/arduino-mega.png'),
(18, 'Arduino Nano', 'Arduino Nano ATmega328P CH340G is a small, complete, and breadboard-friendly board based on the ATmega328. It is fully compatible with the official Arduino board.', 'Microcontrollers', 200, 65, 'assets/products/microcontrollers/arduino-nano.png'),
(19, 'Gizduion Lin-Uno', 'Uses the same micro controller as the Arduino Uno R3 (ATMEGA328P + LIN SOC), and is 100% code compatible with the popular Arduino Uno board. LIN (Local Interconnect Network) is a robust hardware and software serial network used to connect and communicate with automotive components.', 'Microcontrollers', 485, 35, 'assets/products/microcontrollers/gizLIN.png'),
(20, 'STM32 Mini Board', 'SWD interface for dowloading and debugging. Micro USB port for connectivity and uses the STM32F103C8T6 controller', 'Microcontrollers', 149, 55, 'assets/products/microcontrollers/stm32.png'),
(21, 'Assorted Resistor 1/4w', 'This will come as 20pcs of assorted resistor values that can be 120 ,200, 500, 1k, 2k, and 10k resistance', 'Passive', 12, 500, 'assets/products/passive/assorted-resistor.png'),
(22, 'B10K Potentiometer', '10KB Linear Potentiometer PC Solderable', 'Passive', 12, 500, 'assets/products/passive/pot-10k.png'),
(23, '360 Rotary Encoder', 'A rotary encoder has a fixed number of positions per revolution. These positions are easily felt as small “clicks” you turn the encoder. On one side of the switch there are three pins. They are normally referred to as A, B and C. Needs 5v DC power.', 'Passive', 38, 250, 'assets/products/passive/rotary-encoder.png'),
(24, 'Chemi-con KYB 100uF 25V Low ESR Capacitor', 'KYB 100uF/25V<br>Genuine Nippon Chemi-con Radial Electrolytic Capacitor<br>Long Life, Low Impedance<br>Tolerance: +/- 20%<br>Impedance: 0.29R @ 20C 100KHz', 'Passive', 5, 100, 'assets/products/passive/capacitor-electrolytic.png'),
(25, 'Comax Slide Switch', 'SK-22F12 2P2T Contact Rating: 0.3A @ 30VDC<br>Initial Contact Resistance: 30mR max', 'Passive', 12, 250, 'assets/products/passive/switch-slide.png'),
(26, 'DC-DC Buck Converter 3A MP1584EN', 'High frequency step-down switching regulator with an integrated internal high-side high voltage power MOSFET. It provides 3A output with current mode control for fast loop response and easy compensation. 4.5v to 28v input range', 'Passive', 80, 100, 'assets/products/passive/buck-converter.png'),
(27, 'DC-DC Boost Converter LM2577', 'LM2577 series of regulators are monolithic integrated circuits that provide all the active functions for a step-up switching regulator, capable of driving a 3A load with line and load regulation.', 'Passive', 50, 100, 'assets/products/passive/lm2577s.png'),
(28, '25Pcs Tactile Push Button Switch', 'Tactile Push Button Switch Momentary Tact & Caps kit. Caps dimension: 12.7mm (.5\" ) x 7.5mm (.3\" )', 'Passive', 149.75, 75, 'assets/products/passive/button-kit.png'),
(29, '5mm RGB LED', 'By controlling each color with a PWM from your microcontroller, you can change the intensity of each red, green, and blue LED to form other colors. This component also comes very handy as an indicator LED because you can display different status with minimal wiring and space.', 'Passive', 14.75, 100, 'assets/products/passive/led-rgb.png'),
(30, '5mm LED Assorted Color', '20pcs of color white, yellow, red, blue, and green LEDs. It is a 5mm LED.', 'Passive', 25, 100, 'assets/products/passive/led-assorted.png'),
(31, 'YF-S201 Flowmeter 1-30L/min Liquid Flow Sensor', 'Flow rate pulse characteristics: Frequency (Hz) = 7.5 * Flow rate (L/min). Needs 5-18v DC', 'Sensors', 199, 15, 'assets/products/sensors/flow-sensor.png'),
(32, 'Soil Moisture Hygrometer Sensor', 'Simple soil moisture probe has a trimmer presetable digital output that goes to logic low whenever the soil moisture value exceeds the preset level. Needs 3-5v DC.', 'Sensors', 58, 50, 'assets/products/sensors/soil-moisture.png'),
(33, 'E201-c-9 PH Probe', 'PH Range: 0-14. BNC Termination. Probe is the only thing included.', 'Sensors', 397, 10, 'assets/products/sensors/ph-probe.png'),
(34, 'GP2Y1014AU Dust Particle Sensor', 'tiny six-pin analog output optical air quality/optical dust sensor that is designed to sense dust particles in the air. It works on the principle of laser scattering. Inside the sensor module, an infrared emitting diode and a photosensor are diagonally arranged near the air inlet hole. Needs 4.5 to 5.5v DC.', 'Sensors', 350, 13, 'assets/products/sensors/GP2Y1014AU.png'),
(35, 'PIR motion sensor PDMIZ', 'Single channel PIR motion sensor with White Fresnel lens and has wide horizontal coverage. Needs 3.3 - 5v DC', 'Sensors', 79, 40, 'assets/products/sensors/pir-sensor.png'),
(36, 'MQ-7 Carbon Monoxide Sensor', 'Power Supply: 5VDC; Output: Digital(1 bit) + Analog', 'Sensors', 95, 106, 'assets/products/sensors/mq-7.png'),
(37, 'AC Power Analyzer 20Amps', 'AC Power Analyzer 20A/4400VA in an acrylic shell. AC Only Power Analyzer utilizing Hall Effect current sensor for greater power handling capacity.', 'Sensors', 970, 53, 'assets/products/sensors/power-analyzer.png'),
(38, 'Line Tracking Sensor/ Proximity Sensor', 'KY-033 Sensor is built using TCRT5000 IR sensor. It is used primarily as line tracking sensor in DIY robots. Needs 2.5-12v DC.', 'Sensors', 49, 450, 'assets/products/sensors/line-tracing.png'),
(39, 'DHT11 Humidity and Temperature Sensor', 'Features a temperature & humidity sensor complex with a calibrated digital signal output. By using the exclusive digital-signal-acquisition technique and temperature & humidity sensing technology, it ensures high reliability and excellent long-term stability. Needs 3-5v DC.', 'Sensors', 82, 108, 'assets/products/sensors/dht11-humidity.png'),
(40, 'HMC5883L Tri-axis Electronic Compass', 'Can be used in strong magnetic field environments with a 1 degree to 2 degrees Compass Heading Accuracy. The HMC5883L Digital Compass is a surfacemount, multi chip designed for low field magnetic sensing with a digital interface for applications such as low cost compassing and magnetometry. Needs 3-5v DC.', 'Sensors', 155, 128, 'assets/products/sensors/HMC5883L-compass.png'),
(41, '1.3\" I2C OLED Display', 'OLED monochrome 128x64 dot matrix display module with I2C Interface. It is perfect when you need an ultra-small display.', 'Display', 450, 50, 'assets/products/display/I2C-OLED.png'),
(42, 'LCD I2C 1602 BLUE', '16x2 Character LCD Module with I2C Board. Need 5v DC power.', 'Display', 145, 105, 'assets/products/display/lcd-16by2.png'),
(43, '2004 LCD Display Module Blue', 'Standard Single -in-line pinout 20 characters x 4 lines Blue LCD Module. STN White display on Blue backlight/background (Blue). Need 5v DC power.', 'Display', 210, 55, 'assets/products/display/lcd-20by4.png'),
(44, 'MAX7219 Dot Matrix', 'Single 8x8 d3.75mm Dot Matrix Module w/ MAX7219 Driver Chip. LED color is red', 'Display', 120, 65, 'assets/products/display/dot-matrix.png'),
(45, '3.5 inch 480x320 TFT Display with Touch Screen for Raspberry Pi', 'It features a 3.5\" display with 480x320 16-bit color pixels and a resistive touch overlay. It\'s designed to fit nicely not only to the Pi Model A or B but also works perfectly fine with the Model B+/2B/3B.', 'Display', 1000, 25, 'assets/products/display/RaspberryPi-TouchScreenLCD.png'),
(46, 'Jumper Wire Male-Female', '40 pins/pcs Ribbon Jumper Wire. 20 cm length and terminates Male to Female pin header.', 'Wirings', 69, 200, 'assets/products/wirings/jumperWire-MF.png'),
(47, 'Alligator Clip Wire 10s', 'Jumper Wire with Double Ended Alligator Clip Termination. Length of 32cm and having 10 wires in total. Color may vary.', 'Wirings', 109, 100, 'assets/products/wirings/aligatorWire-clip.png'),
(48, 'Breadboard Wires', 'Breadboarding Wires 65 pcs<br>having various length and colors.', 'Wirings', 84, 150, 'assets/products/wirings/breadboard-wires.png'),
(49, 'Jumper Wire Male-Male', '40 pins/pcs Ribbon Jumper Wire. 20 cm Male to Male', 'Wirings', 69, 200, 'assets/products/wirings/jumperWire-MM.png'),
(50, 'USB Cable 9V Converter', 'Converts 5V USB power to 9 VDC on a d5.4 x d2.1mm barrel plug. It is a USB cable with a built-in boost converter. Can only handle up to 800mA current.', 'Wirings', 79, 55, 'assets/products/wirings/usbCable-9vConverter.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`) USING HASH;

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userId` (`userId`,`productId`),
  ADD KEY `productId` (`productId`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `account` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
