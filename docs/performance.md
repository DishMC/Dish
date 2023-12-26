# Performance Changes

This is a list of changes that Dish does to vanilla code for performance reasons.

- ChunkPos
  - Generate asLong once
- FlowingFluid
  - Prevent chunks from loading by flowing fluids
- ServerEntity
  - Modify ChunkMap to expose seenBy set
  - Prevent maps from being updated if there are no seen players
