package com.redsun.bos.ws;

import java.util.List;

import javax.jws.WebService;

import com.redsun.bos.entity.Waybilldetail;

/**
 * 运单服务接口
 * @author 骄傲的大树
 *
 */
@WebService
public interface IWaybillWs {

	/**
	 * 查询运单详情
	 * @param id
	 * @return
	 */
	List<Waybilldetail> waybilldetailList(Long sn);
	
	/**
	 * 在线预约下单
	 * @param id
	 * @param toAddress
	 * @param addressee
	 * @param tele
	 * @param info
	 * @return
	 */
	Long addWaybill(Long id, String toAddress, String addressee, String tele, String info);
}
