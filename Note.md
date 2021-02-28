# Parse Object
- Parse Object Layout (get all fields, offset)
- Get memory addr
- Calculate offset of primitive 
- Get object field addr

# Object
- Object Layout
  - Class Layout
    - List Of:
        - Header
            - Offset
            - Size
        - Field
            - Name
            - Offset
            - Size
  - Array Layout
    - 
  - List Of:
      - Primitive Field
        - Is Static?
        - Value
        - Mem Address
      - Object Field
        - Is Static?
        - Mem Address
        - Object Layout
      - Primitive Array Field
        - Is Static?
        - Length
        - Values
        - Mem Address
        - Array Layout
      - Object Array Field
        
# Step of Init
- User Create (Get Instance or new?) 
- Attach Debugger (throw exception if not attachable, tell them to add allow self attach param)
- Start Websocket Server

# Step of user
- User create map
- Send map to ui
