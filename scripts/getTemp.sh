#!/bin/bash

# Get temperature from koponyeg.hu
BudapestTepmerature="$(wget -qO- http://koponyeg.hu/t/Budapest)"
result=${BudapestTepmerature}
# example text to match
# <div class="actual_inline temp_red" id="temperature_text"> 20&deg;C </div>
regexp='<div\s+class="[^"]+"\s+id="temperature_text">\s*([0-9]+)&deg;C\s*</div>'
[[ $result =~ $regexp ]]
outerTemperature=${BASH_REMATCH[1]}
echo $outerTemperature
