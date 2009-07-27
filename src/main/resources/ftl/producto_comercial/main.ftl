<#compress>
	-- @Started
	-- Borrar asociaciones
	DELETE FROM T_CONF_PROD_COM_TEMPLATE_DETAL
	--		INSERCION DE PRODUCTOS COMERCIALES		--
	<#list repository.getSheet('Producto Comercial').getRows() as row>
		<#if row.getColumn('S') == 'Si' >
			<#assign subquery_producto_tecnico = "SELECT p_id FROM t_producto_tecnico_papel WHERE A_DESCRIPCION = '${row.getColumn('I')}'" >
			<#assign subquery_tipo_operacion = "SELECT p_id FROM T_TIPO_OPERACION_COMERCIAL WHERE upper(A_DESCRIPCION) = upper('${row.getColumn('K')}')" >
			<#if row.getColumn('K') == "null">
				<#assign subquery_tipo_operacion = 'NULL'>
			</#if>
			<#assign subquery_nivel_est = "SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('C')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('B')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${row.getColumn('A')}') )" >
			<#if row.getColumn('E') == '-'>
				<#assign subquery_nivel_est ="SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('E')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('C')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('B')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${row.getColumn('A')}') ) ) )" >
			</#if>			
			<#assign id_geometria = "NULL">
			<#if row.getColumn('S') == '-'>
				<#assign id_geometria="SELECT P_ID FROM T_GEOMETRIA_PAPEL WHERE A_NOMBRE = '${row.getColumn('S')}'">
			</#if>
			<#assign bool_anuncioempadronado='N'>
			<#if row.getColumn('L') == 'si'>
				<#assign bool_anuncioempadronado='Y'>
			</#if>
			<#-- 
			INSERT INTO T_PROD_COMERCIAL (P_ID,A_DESCRIPCION,A_NOMBRE) VALUES (S_PROD_COMERCIAL.NEXTVAL,'${row.getColumn('H')}','${row.getColumn('G')}');
			INSERT INTO T_PROD_COM_PAPEL_CLAS_UBIC (P_ID, F_NIVEL_EST_PAPEL_CLASIFICADOS, F_PRODUCTO_PAPEL_CLASIFICADOS) VALUES ( S_PROD_COM_PAPEL_CLAS_UBIC.NEXTVAL, (SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('C')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('B')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${row.getColumn('A')}') ) ) ), ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${row.getColumn('A')}') ) ); 
			INSERT INTO T_PRODUCTO_COM_PAPEL_CLAS (P_ID,A_ANUNCIANTE_EMPADRONADO,F_TIPO_OPERACION, F_PRODUCTO_TECNICO, F_NIVEL_ESTR_PAPEL_CLASIF, F_PROD_COM_PAPEL_CLAS_UBIC) VALUES (S_PROD_COMERCIAL.CURRVAL, ${bool_anuncioempadronado}, (${subquery_tipo_operacion}), (${subquery_producto_tecnico}), (${subquery_nivel_est}), S_PROD_COM_PAPEL_CLAS_UBIC.CURRVAL ); 
			INSERT INTO T_RANGO_FECHA (P_ID,A_DESCRIPCION,A_FECHA_DESDE,A_FECHA_HASTA) VALUES (S_RANGO_FECHA.NEXTVAL, 'Producto Comercial',TO_DATE('31-12-1899', 'DD-MM-YYYY'),TO_DATE('31-12-9999', 'DD-MM-YYYY'));
			INSERT INTO T_CONFIG_PROD_COM_PAPEL_CLASIF (P_ID,F_GEOMETRIA,F_PRODUCTO_COMERCIAL) VALUES (S_RANGO_FECHA.CURRVAL,( ${id_geometria}),S_PROD_COMERCIAL.CURRVAL); 	
			INSERT INTO T_CONF_PROD_COM_CANAL_ENTRADA (F_CONFIG_PROD_COMERCIAL,F_CANAL_ENTRADA) VALUES (S_RANGO_FECHA.CURRVAL, 1); 
			-->
			<#include 'templates.ftl'>
		</#if>
	</#list>
	-- @End ${timer.elapsedTimeMessage()}
</#compress>