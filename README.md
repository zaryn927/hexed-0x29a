# Hexed-0x29a

## Summary
An original 8-bit top-down RPG for Android where players move around a 2D world to collect artifacts
in order to lift the hex.

## Current State
Player can move around the map rectilinearly. As the player moves away from the center, the map scales larger to simulate
the character shrinking. Artifacts are created and rendered on screen as long as they do not belong to the player.
Walking over artifacts will cause the player to take ownership of the artifact, and it will no longer appear in the world.
Attempting to walk past the edge of the map or through the base of a solid object(e.g. trees, stumps, rocks, logs, pillars) 
will result in a collision detection and stop the player from continuing in that direction.

## Known Bugs
  ### Collision Detection
  Running into a solid object by either moving down or to the right will cause a sticking effect where the player will
  only be able to move in the direction opposite the one the player was travelling when collision was detected.
  ### Random Gliding
  Occasionally the player will continue moving in the last direction set without player input or walk animation
  until they collide with an object or an edge. Player is immobile after this. Occurs very rarely and has unknown cause.
  Likely will not fix.
  ### Crash on Resume
  Since the surface view is still holding values from database instead of getting them from the activitiy's helper, 
  when user exits to home from the main game and then tries to reopen the app, it will crash.
  
## Planned Improvements
  ### Organize and Clean Up Code
  ### Server Connection
  ### High Score Table
  ### Procedurally Generated Terrain Using Perlin Noise and Open Game Art
  ### May Investigate Using OpenGL ES Rather Than Canvas
  ### Audio
  ### Update Database Tables for Relevance as Necessary
  ### Fix Collision Detection
  ### Fix Crash on Resume
  ### Display Collection of Artifacts and Timer
  ### Pause Functionality
  
  
