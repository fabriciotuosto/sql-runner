--		INSERCION DE T_FILTRO_ORDENAMIENTO_AVISO	--
<#list repository.getSheet('T_FILTRO_ORDENAMIENTO_AVISO').getRows(2) as row>
	<#if row.getColumn('H') == 'Si'>
		<#assign orden_padre="SELECT P_ID FROM T_ORDENAMIENTO_AVISO WHERE UPPER(A_DESCRIPCION) = UPPER('${row.getColumn('E')}') AND F_ID_NIVEL_EST_PAPEL = (SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('D')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('C')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('B')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${row.getColumn('A')}') ) ) ) )" >
		<#if row.getColumn('D') = 'NULL'>
			<#assign orden_padre="SELECT P_ID FROM T_ORDENAMIENTO_AVISO WHERE UPPER(A_DESCRIPCION) = UPPER('${row.getColumn('E')}') AND F_ID_NIVEL_EST_PAPEL = (SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('C')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('B')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${row.getColumn('A')}') ) ) )" >
		</#if>
		<#assign tipo_operacion="SELECT p_id FROM T_TIPO_OPERACION_COMERCIAL WHERE upper(A_DESCRIPCION) = upper('${row.getColumn('F')}')">
		<#assign agrup_tecnico="SELECT p_id FROM T_AGRUP_PRODUCTOS_TECNICOS WHERE upper(A_DESCRIPCION) = upper('${row.getColumn('G')}')">
		INSERT INTO T_FILTRO_ORDENAMIENTO_AVISO ( P_ID, F_ID_OPERACION_COM, F_ID_AGRUP_PROD_TECNICOS, F_ID_ORDENAMIENTO_AVISO ) VALUES ( S_FILTRO_ORDENA_AVISO.NEXTVAL,(${tipo_operacion}),(${agrup_tecnico}), (${orden_padre}));
	</#if>
</#list>