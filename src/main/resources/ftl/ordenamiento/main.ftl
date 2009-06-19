<#compress>
--      @Started
--      BORRADO DE ORDENAMIENTOS        -- 
DELETE FROM t_lista_item_hash_valorizada;
DELETE FROM t_item_orden_av;
DELETE FROM t_filtro_ordenamiento_aviso;
DELETE FROM t_lista_item_hash;
DELETE FROM t_ordenamiento_aviso_val;
DELETE FROM t_ordenamiento_aviso;
--		INSERCION DE ORDENAMIENTOS		--
<#include 'ordenamiento_aviso.ftl'>
<#include 'filtro_ordenamiento_aviso.ftl'>
<#include 'lista_item_hash.ftl'>
<#include 'item_orden_av.ftl'>
<#include 't_lista_item_hash_valorizada.ftl'>
-- @End ${timer.elapsedTimeMessage()}
</#compress>