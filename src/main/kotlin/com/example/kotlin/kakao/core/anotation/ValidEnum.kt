package com.example.kotlin.kakao.core.anotation

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

// 이 어노테이션을 어느 요소에 적용할 수 있는지 설정. 여기서는 필드에만
@Target(AnnotationTarget.FIELD)
// 런타임 시간 유지 정책
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
// 해당 어노테이션 검증 위한 검증기 지정
@Constraint(validatedBy = [ValidEnumValidator::class])
// 커스텀 어노테이션 @ValidEnum
annotation class ValidEnum(
    // 유효성 실패시 메시지
    val message: String = "Invalid enum value",
    // 유효성 검사 적용할 범주
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    // 검증 대상이 되는 enum 클래스
    val enumClass: KClass <out Enum<*>>,
)

// 어노테이션 검증하는 커스텀 검증기
class ValidEnumValidator : ConstraintValidator<ValidEnum, Any> {

    // 코틀린은 변수 선언시 초기값 할당해야 함.
    // lateintit 쓰면 나중에 값 할당 가능. var와 사용 가능
    private lateinit var enumValues: Array<out Enum<*>>

    override fun initialize(annotation: ValidEnum) {
        enumValues = annotation.enumClass.java.enumConstants
    }
    override fun isValid(value: Any?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) {
            return true;
        }
        return enumValues.any {
            it.name == value.toString()
        }
    }
}