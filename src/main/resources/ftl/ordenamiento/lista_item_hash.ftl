--		INSERCION DE T_LISTA_ITEM_HASH	--
<#list repository.getSheet('T_LISTA_ITEM_HASH').getRows(2) as row>
	<#if row.getColumn('G') == 'Si'>
		<#assign orden_padre="SELECT P_ID FROM T_ORDENAMIENTO_AVISO WHERE upper(A_DESCRIPCION) = upper('${row.getColumn('E')}') AND F_ID_NIVEL_EST_PAPEL = (SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('D')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('C')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('B')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${row.getColumn('A')}') ) ) ) )" >
		<#if row.getColumn('D') == "NULL">
			<#assign orden_padre="SELECT P_ID FROM T_ORDENAMIENTO_AVISO WHERE UPPER(A_DESCRIPCION) = UPPER('${row.getColumn('E')}') AND F_ID_NIVEL_EST_PAPEL = (SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('C')}') AND F_NIVEL_ESTRUCTURA_PADRE = ( SELECT p_id FROM T_NIVEL_EST_PAPEL_CLASIFICADOS WHERE upper(A_NOMBRE) = upper('${row.getColumn('B')}') AND F_PROD_PAPEL_CLASIFICADOS = ( SELECT p_id FROM T_PRODUCTO WHERE upper(a_nombre) = upper('${row.getColumn('A')}') ) ) ) ">
		</#if>
		INSERT INTO T_LISTA_ITEM_HASH ( P_ID, A_HASHTEXT, F_ID_ORDENAM_AVISO ) VALUES ( S_LISTA_ITEM_HASH.NEXTVAL, '${row.getColumn('F')}', (${orden_padre}));
	</#if>
</#list>