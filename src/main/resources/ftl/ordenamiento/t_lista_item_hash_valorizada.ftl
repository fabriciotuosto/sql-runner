--		INSERCION DE T_FILTRO_ORDENAMIENTO_AVISO	--
<#list repository.getSheet('T_LISTA_ITEM_HASH_VALORIZADA').getRows(2) as row>
	<#if row.getColumn('K') == 'Si'>
		<#assign item_ord_av="SELECT P_ID FROM T_ITEM_ORDEN_AV WHERE A_VALOR = '${row.getColumn('G')}' AND F_ID_ORDEN_AV = (SELECT P_ID FROM T_ORDENAMIENTO_AVISO WHERE UPPER(A_DESCRIPCION) = UPPER('${row.getColumn('E')}') AND F_ID_NIVEL_EST_PAPEL = (SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('D')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('C')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('B')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${row.getColumn('A')}') ) ) ) ) ) " >
		<#if row.getColumn('H') != 'NULL' >	
				<#assign item_ord_av="SELECT P_ID FROM T_ITEM_ORDEN_AV WHERE upper(A_VALOR) = upper('${row.getColumn('G')}') AND F_ITEM_ORDEN_AV_PADRE IN (SELECT P_ID FROM T_ITEM_ORDEN_AV WHERE A_VALOR = '${row.getColumn('H')}') AND F_ID_ORDEN_AV = (SELECT P_ID FROM T_ORDENAMIENTO_AVISO WHERE A_DESCRIPCION = '${row.getColumn('E')}' AND F_ID_NIVEL_EST_PAPEL = (SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('D')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('C')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('B')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${row.getColumn('A')}') ) ) ) ) ) ">
				<#if row.getColumn('D') == 'NULL' >
					<#assign item_ord_av="SELECT P_ID FROM T_ITEM_ORDEN_AV WHERE upper(A_VALOR) = upper('${row.getColumn('G')}') AND F_ITEM_ORDEN_AV_PADRE IN (SELECT P_ID FROM T_ITEM_ORDEN_AV WHERE A_VALOR = '${row.getColumn('H')}') AND F_ID_ORDEN_AV = (SELECT P_ID FROM T_ORDENAMIENTO_AVISO WHERE A_DESCRIPCION = '${row.getColumn('E')}' AND F_ID_NIVEL_EST_PAPEL = (SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('C')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('B')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${row.getColumn('A')}') ) ) ) ) )" >				
				</#if>
		</#if>
		INSERT INTO T_LISTA_ITEM_HASH_VALORIZADA ( P_ID, A_HASHTEXT, A_VALOR, F_ID_ITEM_ORDENAM_AVISO ) VALUES ( S_LISTA_ITEM_HASH_VALORIZADA.NEXTVAL, '${row.getColumn('I')}', '${row.getColumn('J')}', (${item_ord_av}));
	</#if>	
</#list>