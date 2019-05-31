package com.d2c.behavior.services.bak;

/**
 * 前端用户行为调用
 *
 * @author wull
 */
//@Component
public class AppEventServiceImpl implements AppEventService {
//	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	@Autowired
//	private SessionMongoService sessionService;
//	@Autowired
//	private EventMongoService eventService;
//	
//	@Autowired
//	private PersonDeviceMongoDao personDeviceMongoDao;
//	@Autowired
//	private PersonMongoDao personMongoDao;
//	@Autowired
//	private PersonLocalMongoDao personLocalMongoDao;
//	
//	/**
//	 * Session校验
//	 * @param sessionId 会话ID
//	 */
//	public SessionDO validate(String sessionId){
//		AssertUt.notEmpty(sessionId, "sessionId不能为空");
//		SessionDO session = sessionService.findSessionById(sessionId);
//		AssertUt.notNull(session, "Session会话不存在，请先开始会话 onLoad...");
//		return session;
//	}
//	
//	/**
//	 * 用户创建会话
//	 * <p>根据sessionId从缓存获取Session
//	 * <br>未登录，根据设备UDID合并数据库消息，并创建Session
//	 */
//	public SessionDO onload(String sessionId, PersonDeviceDO device) {
//		try{
//			return validate(sessionId);
//		}catch (AssertException e) {}
//		
//		AssertUt.notNull(device, "未能获取到设备数据, 请检查...");
//		AssertUt.notNull(device.getUdid(), "设备唯一标识码UDID不能为空");
//		personDeviceMongoDao.save(device);
//		return sessionService.create(device);
//	}
//	
//	/**
//	 * 识别用户
//	 */
//	public SessionDO identify(String sessionId, String phone, Object props) {
//		SessionDO session = validate(sessionId);
//		PersonDO person = personMongoDao.findByPhone(phone);
//		if(person == null){
//			//新建用户
//			person = new PersonDO(phone, props);
//			personMongoDao.save(person);
//			
//			//关联设备到用户
//			PersonDeviceDO device = session.getDevice(); 
//			device.setPersonId(person.getId());
//			personDeviceMongoDao.save(device);
//		}
//		session.setPerson(person);
//		return sessionService.save(session);
//	}
//
//	/**
//	 * 用户调用事件
//	 */
//	public EventDO event(String sessionId, String event) {
//		SessionDO session = validate(sessionId);
//		EventDO bean = new EventDO(session, event);
//		return eventService.saveEvent(bean);
//	}
//
//	/**
//	 * 定时获取用户定位
//	 */
//	public PersonLocalDO onLocal(String sessionId, LatLng point) {
//		SessionDO session = validate(sessionId);
//		PersonLocalDO locat = new PersonLocalDO(session, point);
//		return personLocalMongoDao.insert(locat);
//	}
}
