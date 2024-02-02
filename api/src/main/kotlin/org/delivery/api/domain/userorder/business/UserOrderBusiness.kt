package org.delivery.api.domain.userorder.business

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.extern.slf4j.Slf4j
import org.delivery.api.common.Log
import org.delivery.api.domain.store.converter.StoreConverter
import org.delivery.api.domain.store.service.StoreService
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter
import org.delivery.api.domain.storemenu.service.StoreMenuService
import org.delivery.api.domain.user.model.User
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse
import org.delivery.api.domain.userorder.converter.UserOrderConverter
import org.delivery.api.domain.userorder.producer.UserOrderProducer
import org.delivery.api.domain.userorder.service.UserOrderService
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService
import org.delivery.common.annotation.Business
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus

@Business
class UserOrderBusiness (
    private val userOrderService: UserOrderService,
    private val userOrderConverter: UserOrderConverter,

    private val storeMenuService: StoreMenuService,
    private val storeMenuConverter: StoreMenuConverter,

    private val storeService: StoreService,
    private val storeConverter: StoreConverter,

    private val userOrderMenuService: UserOrderMenuService,
    private val userOrderMenuConverter: UserOrderMenuConverter,

    private val userOrderProducer: UserOrderProducer
) {
    companion object: Log  // Log를 상속받는다.

    fun userOrder(
        user: User?,
        body: UserOrderRequest?
    ): UserOrderResponse {
        // 가게 찾기
        val storeEntity = storeService.getStoreWithThrow(body?.storeId)
        // 주문한 메뉴 찾기
        val storeMenuEntityList = body?.storeMenuIdList
            ?.mapNotNull { storeMenuService.getStoreMenuWithThrow(it) }
            ?.toList()

        // 주문
        val userOrderEntity = userOrderConverter.toEntity(
            user = user,
            storeEntity = storeEntity,
            storeMenuEntityList = storeMenuEntityList
        ).run {
            userOrderService.order(this)
        }

        // 매핑
        val userOrderMenuEntityList = storeMenuEntityList?.stream()
            ?.map { it -> userOrderMenuConverter.toEntity(userOrderEntity, it) }
            ?.toList()

        // 주문내역 기록 남기기
        userOrderMenuEntityList?.forEach { it -> userOrderMenuService.order(it) }

        // 비동기로 가맹점에 주문 알리기
        userOrderProducer.sendOrder(userOrderEntity)

        return userOrderConverter.toResponse(userOrderEntity)
    }

    fun current(
        user: User?
    ): List<UserOrderDetailResponse>? {
        val userOrderEntityList = userOrderService.current(user?.id)

        return userOrderEntityList.map {

            var userOrderMenuEntityList = it.userOrderMenuEntityList
                ?.filter { it.status == UserOrderMenuStatus.REGISTERED }
                ?.toList()

            var storeMenuEntityList = userOrderMenuEntityList
                ?.mapNotNull { it.storeMenu }
                ?.toList()

            var storeEntity = it.store

            UserOrderDetailResponse(
                userOrderResponse = userOrderConverter.toResponse(it),
                storeResponse = storeConverter.toResponse(storeEntity),
                storeMenuResponses = storeMenuConverter.toResponse(storeMenuEntityList)
            )
        }.toList()
    }

    fun history(
        user: User?
    ): List<UserOrderDetailResponse>? {
        val userOrderEntityList = userOrderService.history(user?.id)

        return userOrderEntityList.map {

            var userOrderMenuEntityList = it.userOrderMenuEntityList
                ?.filter { it.status == UserOrderMenuStatus.REGISTERED }
                ?.toList()

            var storeMenuEntityList = userOrderMenuEntityList
                ?.mapNotNull { it.storeMenu }
                ?.toList()

            var storeEntity = it.store

            UserOrderDetailResponse(
                userOrderResponse = userOrderConverter.toResponse(it),
                storeResponse = storeConverter.toResponse(storeEntity),
                storeMenuResponses = storeMenuConverter.toResponse(storeMenuEntityList)
            )
        }.toList()
    }

    fun read(
        user: User?,
        orderId: Long?
    ): UserOrderDetailResponse {

        return userOrderService.getUserOrderWithoutStatusWithThrow(orderId, user?.id)
            .let {userOrderEntity ->

                val storeMenuEntityList = userOrderEntity.userOrderMenuEntityList
                    ?.stream()
                    ?.filter { it.status == UserOrderMenuStatus.REGISTERED }
                    ?.map { it.storeMenu }
                    ?.toList()
                    ?: listOf() // 아무것도 없으면 빈 리스트 리턴

                // 자세한 사용자 주문 응답 생성
                UserOrderDetailResponse(
                    userOrderResponse = userOrderConverter.toResponse(userOrderEntity),
                    storeResponse = storeConverter.toResponse(userOrderEntity.store),
                    storeMenuResponses = storeMenuConverter.toResponse(storeMenuEntityList)
                )
        }
    }
}