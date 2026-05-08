I have adea to add combination activation, same as default activator but with sequence like LEFT_CLICK-RIGHT_CLICK then will cast the skill

Example Config
```

combo_activator:
  Abilites:
  - skill: example
    combo:
      pattern: ["RIGHT_CLICk", "RIGHT_CLICk", "LEFT_CLICk"]
      complete_duration: 20t
	- skill: example
	  combo:
		  pattern: ["BACK", "BACK"]
			complete_duration: 4t

```