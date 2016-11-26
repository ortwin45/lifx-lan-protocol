# LIFX4J
Java library for controlling [LIFX](https://lan.developer.lifx.com/) bulbs over the LAN. This library is based 
on LAN Protocol v2.0 as it is described on the LIFX [Developer Zone](https://lan.developer.lifx.com/).

## Current State
The most basic functionality is supported
* Discover light bulbs
* Turn bulbs on and off
* Change color

## How to
Create an instance of LifxClient and use this to send messages to the bulbs. 
```
LifxClient lifxClient = new LifxClient();
lifxClient.sendMessage(new SetPower(true));
        
```
This might be the easiest to get started but not the most recommended. See the [protocol documentation](https://lan.developer.lifx.com/) 
for more details. 

## Example
* See the tests for some examples on how to turn the lights on and off and change the color.
* See my [other repository](https://github.com/ortwin45/rpi-lifx-button-box) for an example of a Raspberry Pi button box controlling the bulbs in the kid's room. 
 

