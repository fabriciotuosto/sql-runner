--		INSERCION DE T_FILTRO_ORDENAMIENTO_AVISO	--
<#list repository.getSheet('T_ITEM_ORDEN_AV').getRows(2) as row>
	<#if row.getColumn('J') == 'Si'>
		<#assign orden_av_padre="NULL" >
		<#if row.getColumn('G') != 'NULL' >
			<#list repository.getSheet('T_ITEM_ORDEN_AV').getRows(2) as sub_row>
				<#assign orden_av_padre="SELECT P_ID FROM T_ITEM_ORDEN_AV WHERE upper(A_VALOR) = upper('${sub_row.getColumn('H')}') AND F_ID_ORDEN_AV = (SELECT P_ID FROM T_ORDENAMIENTO_AVISO WHERE A_DESCRIPCION = '${sub_row.getColumn('E')}' AND F_ID_NIVEL_EST_PAPEL = (SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${sub_row.getColumn('D')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${sub_row.getColumn('C')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${sub_row.getColumn('B')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${sub_row.getColumn('A')}') ) ) ) ) )" >
				<#if sub_row.getColumn('F') == row.getColumn('G')>
					<#assign orden_av_padre="SELECT P_ID FROM T_ITEM_ORDEN_AV WHERE upper(A_VALOR) = upper('${sub_row.getColumn('H')}') AND F_ID_ORDEN_AV = (SELECT P_ID FROM T_ORDENAMIENTO_AVISO WHERE A_DESCRIPCION = '${sub_row.getColumn('E')}' AND F_ID_NIVEL_EST_PAPEL = (SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${sub_row.getColumn('C')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${sub_row.getColumn('B')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${sub_row.getColumn('A')}') ) ) ) )">
					<#if sub_row.getColumn('D') == 'NULL' >
						<#assign orden_av_padre="SELECT P_ID FROM T_ITEM_ORDEN_AV WHERE upper(A_VALOR) = upper('${sub_row.getColumn('H')}') AND F_ID_ORDEN_AV = (SELECT P_ID FROM T_ORDENAMIENTO_AVISO WHERE A_DESCRIPCION = '${sub_row.getColumn('E')}' AND F_ID_NIVEL_EST_PAPEL = (SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${sub_row.getColumn('C')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${sub_row.getColumn('B')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${sub_row.getColumn('A')}') ) ) ) )"> 
					</#if>
				</#if> 
			</#list>
		</#if>
		<#assign orden_av="SELECT P_ID FROM T_ORDENAMIENTO_AVISO WHERE UPPER(A_DESCRIPCION) = UPPER('${row.getColumn('E')}') AND F_ID_NIVEL_EST_PAPEL = (SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('D')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('C')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('B')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${row.getColumn('G')}') ) ) ) )" >
		<#if row.getColumn('D') = 'NULL'>
			<#assign orden_av="SELECT P_ID FROM T_ORDENAMIENTO_AVISO WHERE UPPER(A_DESCRIPCION) = UPPER('${row.getColumn('E')}') AND F_ID_NIVEL_EST_PAPEL = (SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('C')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('B')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${row.getColumn('G')}') ) ) )" >
		</#if>
		INSERT INTO T_ITEM_ORDEN_AV ( P_ID, A_VALOR, A_INGRESO_MANUAL, F_ID_ORDEN_AV, F_ITEM_ORDEN_AV_PADRE ) VALUES ( S_ITEM_ORDEN_AV.NEXTVAL, '${row.getColumn('H')}', '${row.getColumn('I')}', (${orden_av}), (${orden_av_padre}));
	</#if>
</#list>