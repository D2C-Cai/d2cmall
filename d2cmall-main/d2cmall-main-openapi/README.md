#	OpenApi说明文档

## 快速入门

#### 参数说明

名称			| 	描述                      			
------------|-----------------------------
token		|	token令牌, 申请获得，唯一用户账号ID
timestamp	|	时间校验, 进行超时校验 一般 60s
nonce		|	唯一的请求ID (可选), 防止请求重复提交
sign		|	数据签名，签名后生成

#### 关键字符说明

名称			| 	描述                      			
------------|-----------------------------
privateKey	|	RSA私钥，数字签名使用，保障数据安全勿泄露
signStr		|	被签名字符串
signMd5		|	对signStr进行MD5加密后的数据
params		|	用户提交的参数
			
## 使用文档

#### 申请D2C开放平台权限

向客服申请权限, 申请注明【测试环境】or【生产环境】，获得token和加密私钥
	
	token: 52f77d34-205f-483b-ba3b-38b6f8bb1e48
	加密私钥: MIICdgIBADANBgkqhkiG9w0BAQEFAA....4qqJ74Q2ofSAvsAA
	

	测试环境地址:
	http://	openapi.test.d2cmall.com/openapi
	生产环境地址:
	http://	openapi.d2cmall.com/openapi
	
	
#### 如何签名
	
##### 调用API时需要对请求参数${params}进行签名验证:	
		
获取所有请求参数， 对参数名称按key值ASCII码升序排列，排除token， timestamp， nonce， sign.
	
调用实例:

	URL: http://openapi.test.d2cmall.com/openapi/test?name=abc&age=18&sex=man
	
	排序前${params}: name=abc&age=18&sex=man
	排序后${params}: age=18&name=abc&sex=man

##### 生成signStr需要签名字符串：

	token： token令牌, 申请获得用户账号ID	
	timestamp: 添加请求发出的时间戳，60秒后请求超时
	nonce: 可以是增量数字，也可以是不会重复的随机字符串，作为请求唯一性标识，防止请求重复提交
		nonce 存在: ${token}_${timestamp}_${nonce}_${params}
		nonce 不存在：${token}_${timestamp}_${params}
	
	signStr: 52f77d34-205f-483b-ba3b-38b6f8bb1e48_1533094187721_11538_age=18&name=abc&sex=man
	
##### 对signStr进行MD5加密，生成signMd5

	signMd5: 73e141844b9520c0af2fcda120f266f9

##### 使用privateKey私钥对signMd5进行数字签名
	
	对privateKey私钥进行Base64.decode解码，然后进行RSA私钥加密，获得密文进行Base64.encodeBase64URLSafe 保证URL安全的Base64编码，即可获得sign签名密文
	
	sign: 67olPTwdjpRVk8gwX04doZutxo4...ve06juAyCVFYHUtPsI0jfk

##### 提交请求 可支持 HEAD提交（推荐）和参数提交
	
	HEAD提交: 将token, timestamp, nonce, sign 放入Http的Header中提交
	参数提交: 将token, timestamp, nonce, sign 放入参数中提交

#### JAVA代码调用示例：

	/**
	 * HEAD签名调用示例
	 */
	@ResponseBody
	@RequestMapping(value = "/header", method = RequestMethod.GET)
	public Object findHeader() {
		String url = "http://localhost:8060/openapi/user/test";
		Map<String, Object> params = new HashMap<>();
		params.put("name", "abc");
		params.put("age", 18);
		params.put("sex", "man");
		return RestHelper.get(url, getSignHeader(params), JsonBean.class, params);
	}
	
	/**
	 * 参数签名调用示例
	 */
	@ResponseBody
	@RequestMapping(value = "/param", method = RequestMethod.GET)
	public Object findParam() {
		String url = "http://localhost:8060/openapi/user/test";
		Map<String, Object> params = new HashMap<>();
		params.put("name", "abc");
		params.put("age", 18);
		params.put("sex", "man");
		return RestHelper.get(url, JsonBean.class, getSignParam(params));
	}
	
	/**
	 * HEAD提交签名
	 */
	private Map<String, Object> getSignHeader(Map<String, Object> params) {
		Long timestamp = DateUt.getTimeInMillis();
		String nonce = RandomUt.nextStr(NONCE_MAX);
		String sign = tokenHandler.signToken(token, privateKey, timestamp, nonce, params);
		Map<String, Object> header = new HashMap<>();
		header.put("token", token);
		header.put("timestamp", timestamp);
		header.put("sign", sign);
		header.put("nonce", nonce);
		return header;
	}
	
	/**
	 * 参数提交签名
	 */
	private Map<String, Object> getSignParam(Map<String, Object> params) {
		Long timestamp = DateUt.getTimeInMillis();
		String nonce = RandomUt.nextStr(NONCE_MAX);
		String sign = tokenHandler.signToken(token, privateKey, timestamp, nonce, params);
		params.put("token", token);
		params.put("timestamp", timestamp);
		params.put("sign", sign);
		params.put("nonce", nonce);
		return params;
	}
	
	/**
	 * Token私钥签名
	 */
	public String signToken(String token, String privateKey, Long timestamp, String nonce, Map<String, Object> params) {
		String signStr = getSignString(token, timestamp, nonce, params);
		return RSAUt.encodePrivate(privateKey, MD5Ut.md5(signStr));
	}
	
	/**
	 * 获取需要签名字符串
	 */
	private String getSignString(String token, Long timestamp, String nonce, Map<String, Object> params) {
		return StringUt.join("_", token, timestamp, nonce, getUrlParams(new TreeMap<>(params)));
	}


