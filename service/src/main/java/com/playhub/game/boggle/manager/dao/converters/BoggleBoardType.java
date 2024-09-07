package com.playhub.game.boggle.manager.dao.converters;

import com.playhub.game.boggle.manager.models.BoggleBoard;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class BoggleBoardType implements UserType<BoggleBoard> {

    @Override
    public int getSqlType() {
        return Types.ARRAY;
    }

    @Override
    public Class<BoggleBoard> returnedClass() {
        return BoggleBoard.class;
    }

    @Override
    public boolean equals(BoggleBoard lists, BoggleBoard j1) {
        return lists.equals(j1);
    }

    @Override
    public int hashCode(BoggleBoard lists) {
        return lists.hashCode();
    }

    @Override
    public BoggleBoard nullSafeGet(ResultSet resultSet, int i, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws SQLException {
        Array array = resultSet.getArray(i);
        if (array == null) {
            return null;
        }
        String[][] stringArray = (String[][]) array.getArray();
        List<List<Character>> result = new ArrayList<>();
        for (String[] row : stringArray) {
            List<Character> charList = new ArrayList<>();
            for (String element : row) {
                charList.add(element.charAt(0));
            }
            result.add(charList);
        }
        return new BoggleBoard(result);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, BoggleBoard board, int index, SharedSessionContractImplementor implementor) throws SQLException {
        if (board == null) {
            st.setNull(index, Types.ARRAY);
            return;
        }
        String[][] stringArray = new String[board.size()][];
        for (int i = 0; i < board.size(); i++) {
            List<Character> charList = board.get(i);
            stringArray[i] = charList.stream().map(String::valueOf).toArray(String[]::new);
        }
        Array array = implementor.getJdbcConnectionAccess().obtainConnection().createArrayOf("text", stringArray);
        st.setArray(index, array);
    }

    @Override
    public BoggleBoard deepCopy(BoggleBoard lists) {
        if (lists == null) {
            return null;
        }
        BoggleBoard copy = new BoggleBoard();
        for (List<Character> row : lists) {
            copy.add(new ArrayList<>(row));
        }
        return copy;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(BoggleBoard value) {
        return deepCopy(value);
    }

    @Override
    public BoggleBoard assemble(Serializable serializable, Object o) {
        return null;
    }

    @Override
    public BoggleBoard replace(BoggleBoard detached, BoggleBoard managed, Object owner) {
        return deepCopy(detached);
    }

}
