-- lua 脚本限制用户登录次数
local c
c = redis.call('get',KEYS[1])
-- 如果 c 为 nil，将其设置为 0
if c == nil then
    c = 0
else
-- 调用不超过最大值，则直接返回
 --[[ if  c > tonumber(ARGV[1]) then
    return c;
 end ]]
end
-- 执行计算器自加
c = redis.call('incr',KEYS[1])

if c == 1 then
-- 从第一次调用开始限流，设置对应键值的过期
    redis.call('expire',KEYS[1],ARGV[2])
end
return c;