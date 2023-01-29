package com.gdxsoft.web.doc;

import java.util.ArrayList;

import com.gdxsoft.easyweb.data.DTRow;
import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.utils.msnet.MStr;

public class EwaDocPart {

	public EwaDocPart() {
		this._Children = new ArrayList<EwaDocPart>();
		_CreatedContent = new MStr();
	}

	public String getStrStart() {
		return _StrStart;
	}

	public void setStrStart(String _StrStart) {
		this._StrStart = _StrStart;
	}

	public String getStrEnd() {
		return _StrEnd;
	}

	public void setStrEnd(String _StrEnd) {
		this._StrEnd = _StrEnd;
	}

	public String getStrMid() {
		return _StrMid;
	}

	public void setStrMid(String _StrMid) {
		this._StrMid = _StrMid;
	}

	public ArrayList<EwaDocPart> getChildren() {
		return _Children;
	}

	public void setChildren(ArrayList<EwaDocPart> _Children) {
		this._Children = _Children;
	}

	public EwaDocPart getParent() {
		return _Parent;
	}

	public void setParent(EwaDocPart _Parent) {
		this._Parent = _Parent;
	}

	public int getSqlIdx() {
		return _SqlIdx;
	}

	public void setSqlIdx(int _SqlIdx) {
		this._SqlIdx = _SqlIdx;
	}

	private String _OriCnt;

	public String getOriCnt() {
		return _OriCnt;
	}

	public void setOriCnt(String _OriCnt) {
		this._OriCnt = _OriCnt;
	}

	public int getlocStart() {
		return _locStart;
	}

	public void setlocStart(int _locStart) {
		this._locStart = _locStart;
	}

	public int getlocEnd() {
		return _locEnd;
	}

	public void setlocEnd(int _locEnd) {
		this._locEnd = _locEnd;
	}

	private int _locStart;
	private int _locEnd;

	private String _StrStart;
	private String _StrEnd;
	private String _StrMid;

	private ArrayList<EwaDocPart> _Children;

	private EwaDocPart _Parent;
	private int _SqlIdx;

	private String _Tag;

	public String getTag() {
		return _Tag;
	}

	public void setTag(String _Tag) {
		this._Tag = _Tag;
	}

	private MStr _CreatedContent;

	public MStr getCreatedContent() {
		return _CreatedContent;
	}

	public void setCreatedContent(MStr _CreatedContent) {
		this._CreatedContent = _CreatedContent;
	}

	private DTTable _Table;

	public DTTable get_Table() {
		return _Table;
	}

	public void set_Table(DTTable _Table) {
		this._Table = _Table;
	}

	public DTRow get_CurRow() {
		return _CurRow;
	}

	public void set_CurRow(DTRow _CurRow) {
		this._CurRow = _CurRow;
	}

	private DTRow _CurRow;

}
