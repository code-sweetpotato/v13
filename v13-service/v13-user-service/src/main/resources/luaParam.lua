local key = KEYS[1]
local value = KEYS[2]

local result = redis.call('set',key,value)
return result