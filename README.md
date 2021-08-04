# T-6B EICAS

T-6B EICAS provides the means to build recognition of indications for certain emergencies and failure states in the [T-6B Texan II](https://en.wikipedia.org/wiki/Beechcraft_T-6_Texan_II).

## The following canned emergencies are provided:
* Normal Start
* Hot Start
* No Start
* Hung Start
* Engine Flameout
* Engine Seizure

## Engine Flameout
<img src="/demo.gif" width="300px">

## Building Scenarios
EICAS scenarios are expressed in a JSON format and stored in: /app/src/main/res/raw/.

```json
{
  "initialize": {
    "torque": 54,
    "oilPress": 108,
    "itt": 634,
    "oilTemp": 63,
    "n1": 93,
    "hydPress": 3010,
    "propRpm": 100,
    "fuelFlow": 430
  },
  "execute": {
    "torque": {
      "events": [
        {
          "targetValue": 53,
          "secondsToTarget": 3
        },
        {
          "targetValue": 0,
          "secondsToTarget": 5
        },
        {
          "targetValue": 2,
          "secondsToTarget": 7
        },
        {
          "targetValue": 10,
          "secondsToTarget": 2
        },
        {
          "targetValue": 0,
          "secondsToTarget": 4
        }
      ]
    },
```

The execute object contains execute events for each gauge. The events are executed by the gauge model/controller in FIFO order.
