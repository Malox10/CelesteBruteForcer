# Celeste Grab-Manip Bruteforcer

A tool to bruteforce grab-manip inputs with calculations that reflect Celeste's float behaviour.

# Use

Currently, requires an IDE with kotlin support.
Alternatively contact me on discord at "luzid42" (preferably with an already configured .json file)

Edit the config.json file to set up the bruteforcer and run the main() function afterward.
All solutions will be printed during runtime (unsorted) with information about the initial condition and celeste-studio Inputs.

# config.json settings explanation

- Initial Conditions:
All the starting points from which grab-manip inputs will be simulated.
the "slowFallHeld" boolean value describes if a jump-input is buffered into the manip 
for the corresponding initial condition (good if possible but not needed in most cases)

- Targets:
All target ranges that you want to manip to.

- maxDepth:
the maximum length of the input sequence being simulated (in frames).
Increases runtime exponentially, usual values are between 15 and 25 frames.

- endWithGrab:
set to true if you want the manip to end with a grab input and with |y-speed| < 15.
This allows to simply hold the last grab input without leaving the target range.

- solutionSetting:
"ExactPosition" makes sure you end up exactly in a given target range.
"SubPixelOnly" considers only the subpixel of the target range.

- noGrabFrames:
The frames during which madeline can't grab.

- noSlideFrames:
The frames during which madeline can't slide along the wall.

  
# Recommendations
- Initial conditions should be around 5 or more pixels above the target ranges, otherwise
the bruteforcer will struggle to find solutions if the solutionSetting is set to "ExactPosition".
- In simple cases with a static wall and more than 30f to manip I recommend using the most convenient settings
since it will most likely find a solution that works (endWithGrab: true, "ExactPosition").
