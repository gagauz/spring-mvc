<#include "mail_header.ftl">
<p>
    Вам отправлены контактные данные ученика по заявке #${order.id}: 
    Имя ученика: ${order.pupil.name}<br />
    <#if order.pupil.email??>E-mail: ${order.pupil.email}<br /></#if>
    <#if order.pupil.phone??>Телефон: ${order.pupil.phone}</#if>
</p>
<#include "mail_footer.ftl">