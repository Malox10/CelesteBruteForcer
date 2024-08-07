# Celeste Grab-Manip Bruteforcer

A tool to bruteforce grab-manip inputs with calculations that reflect Celeste's float behaviour.

# Use

Currently, requires either an IDE with kotlin support or using a "gradle run" command in the CelesteBruteForcer directory (JVM: corretto-21).
Alternatively contact me on discord at "luzid42" (preferably with an already configured .json file)

Edit the config.json file to set up the bruteforcer and run the main() function afterward.
All solutions will be printed during runtime (unsorted) with information about the initial condition and celeste-studio Inputs.
At the end of the simulation the fastest solution will be printed again together with the solution-distribution.

# config.json settings explanation

- Initial Conditions:
All the starting points from which grab-manip inputs will be simulated.
the "slowFallHeld" boolean value describes if a jump-input is buffered into the manip 
for the corresponding initial condition (good if possible but not needed in most cases)

- Targets:
All target ranges that you want to manip to (bounds included).

- maxDepth:
the maximum length of the input sequence being simulated (in frames).
Increases runtime exponentially, usual values are between 15 and 25 frames.

- endWithGrab:
set to true if you want the manip to end with a grab input and with |y-speed| < 15.
This allows to simply hold the last grab input without leaving the target range.

- solutionSetting:
"ExactPosition" makes sure you end up exactly in a given target range.
"SubPixelOnly" considers only the subpixel of the target range.

- yGround:
An integer representing the center of the pixel where Madeline is touching the ground. `null` if not applicable.
Allows taking into account the physics changes from the ground.

- noGrabFrames:
The frames during which madeline can't grab.

- noSlideFrames:
The frames during which madeline can't slide along the wall.

  
# Recommendations & miscellaneous
- Initial conditions should be around 5 or more pixels above the target ranges, otherwise
the bruteforcer will struggle to find solutions if the solutionSetting is set to "ExactPosition". The y-speed and y-position values
need to be copied manually from celeste (or by using "Ctrl + Shift + C" in celeste studio to copy the player data to clipboard)
- Target range bounds can be determined by adjusting a "Invoke, Player.MoveV, " command in celeste studio manually.
Usually, finding the first 1 or 2 differing decimal places between upper and lower bound should be enough to find decent solutions.
- In simple cases with a static wall and more than 30f to manip I recommend using the most convenient settings
since it will most likely find a solution that works (endWithGrab: true, "ExactPosition").
- If the wall used for manip is to the left, the "R" inputs need to be changed to "L" inputs.
- If the manip needs to be as fast as possible you can start with the following:
  - add more initial conditions and target ranges (mostly through adjusting slowfall, fastfall and length of diagonal dashes)
  - use the "SubPixelOnly" setting (basically more target ranges)
  - set "endWithGrab" to false, if possible
  - find initial conditions that use the "slowFallHeld" setting (this has a pretty big impact on number of solutions)
  - determine the target ranges more precisely
