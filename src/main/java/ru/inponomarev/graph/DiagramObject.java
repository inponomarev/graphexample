package ru.inponomarev.graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.EnumSet;
import java.util.function.Consumer;

/** Базовый класс объекта, отрисовывающегося на диаграмме. */
public abstract class DiagramObject {

	private static final int BIG_VALUE = 0xFFFF;

	/**
	 * Размер синего квадрата, обозначающего выделение по умолчанию.
	 */
	private static final int L = 4;

	private DiagramObject previous;
	private DiagramObject next;
	private DiagramObject first;
	private DiagramObject last;
	private DiagramObject parent;

	private Font font;
	private Color color;

	private Graphics canvas;
	private double scale;
	private double dX;
	private double dY;

	private static boolean assigned(Object x) {
		return x != null;
	}

	/** Сохраняет свойства передаваемого объекта TCanvas. */
	private void saveCanvasSetup() {
		assert assigned(canvas);
		font = canvas.getFont();
		color = canvas.getColor();
		// TODO: добавьте сюда сохранение всех доступных свойств Canvas
	}

	/** Восстанавливает свойства передаваемого объекта TCanvas. */
	private void restoreCanvasSetup() {
		canvas.setFont(font);
		canvas.setColor(color);
		// TODO: добавьте сюда восстановление всех доступных свойств Canvas

	}

	/**
	 * Возвращает значение свойства Hint.
	 * 
	 * @param hintStr
	 *            буфер, в котором строится строка для хинта
	 */
	protected boolean internalGetHint(StringBuilder hintStr) {
		return false;
	}

	/**
	 * Переводит мировую длину в экранную со сдвигом по X.
	 * 
	 * @param value
	 *            значение мировой длины
	 * @return Экранная длина
	 */
	protected final int scaleX(double value) {
		return scale(value - dX);
	}

	/**
	 * Переводит мировую длину в экранную со сдвигом по Y.
	 * 
	 * @param value
	 *            значение мировой длины
	 * @return Экранная длина
	 */

	protected final int scaleY(double value) {
		return scale(value - dY);
	}

	/**
	 * Переводит мировую длину в экранную.
	 * 
	 * @param value
	 *            значение мировой длины
	 * @return Экранная длина
	 */
	protected final int scale(double value) {
		return (int) Math.round(value * scale);
	}

	/**
	 * Добавляет в конец очереди отрисовки новый подобъект.
	 * 
	 * @param subObj
	 *            добавляемый подобъект
	 */
	protected final void addToQueue(DiagramObject subObj) {
		if (assigned(last)) {
			assert assigned(first);
			assert!assigned(last.next);
			assert!assigned(first.previous);
			last.next = subObj;
			subObj.previous = last;
		} else {
			assert!assigned(first);
			first = subObj;
			subObj.previous = null;
		}
		subObj.next = null;
		subObj.parent = this;
		last = subObj;

	}

	/**
	 * Внутренняя отрисовка выделения объекта.
	 * 
	 * @desc Переопределите данный метод, чтобы реализовать отрисовку,
	 *       специфичную для данного объекта
	 * 
	 * @param canvas
	 *            контекст для отрисовки
	 * @param dX
	 *            сдвиг по X в экранных координатах
	 * @param dY
	 *            сдвиг по Y в экранных координатах
	 */
	protected void internalDrawSelection(Graphics canvas, int dX, int dY) {
		canvas.setColor(Color.BLUE);
		canvas.setXORMode(Color.WHITE);
		// верх лев
		canvas.fillRect(minX() + dX - L, minY() + dY - L, L, L);
		// верх прав
		canvas.fillRect(maxX() + dX, minY() + dY - L, L, L);
		// низ лев
		canvas.fillRect(minX() + dX - L, maxY() + dY, L, L);
		// низ прав
		canvas.fillRect(maxX() + dX, maxY() + dY, L, L);
		canvas.setPaintMode();
	}

	/**
	 * Внутренняя отрисовка объекта.
	 * 
	 * @param canvas
	 *            Контекст для отрисовки.
	 */
	protected void internalDraw(Graphics canvas) {
		// Ничего не делает на уровне DrawObject
	}

	/**
	 * Внутренняя проверка попадания курсора на объект.
	 * 
	 * @desc На уровне DrawObject возвращает False
	 * @param x
	 *            мировая абсцисса
	 * @param y
	 *            мировая ордината
	 */
	protected boolean internalTestHit(double x, double y) {
		return false;
	}

	/**
	 * Перемещение объекта в новую точку.
	 * 
	 * @param dX
	 *            смещение по X
	 * @param dY
	 *            смещение по Y
	 */
	protected void internalDrop(double dX, double dY) {
		// Ничего не делает на уровне DrawObject
	}

	/**
	 * Сдвиг объекта в текущей очереди отрисовки таким образом, чтобы он
	 * отрисовывалсся позднее.
	 */
	protected final void moveUp() {
		if (assigned(next)) {
			if (assigned(previous))
				previous.next = next;
			else if (assigned(parent))
				parent.first = next;
			next.previous = previous;
			previous = next;
			next = next.next;
			previous.next = this;
			if (assigned(next))
				next.previous = this;
			else if (assigned(parent))
				parent.last = this;
		}

	}

	/**
	 * Сдвиг объекта в текущей очереди отрисовки таким образом, чтобы он
	 * отрисовывалсся ранее.
	 */
	protected final void moveDown() {
		if (assigned(previous))
			previous.moveUp();
	}

	/** Первый подобъект очереди отрисовки. */
	protected final DiagramObject getFirstSubObj() {
		return first;
	}

	/** Последний подобъект очереди отрисовки. */
	protected final DiagramObject getLastSubObj() {
		return last;
	}

	/** Следующий объект. */
	protected final DiagramObject getNext() {
		return next;
	}

	/** Предыдущий объект. */
	protected final DiagramObject getPrevious() {
		return previous;
	}

	/**
	 * Фактор увеличения.
	 */
	protected final double getScale() {
		return scale;
	}

	/**
	 * Минимальная мировая абсцисса.
	 */
	protected double getMinX() {
		return Double.NaN;
	}

	/**
	 * Минимальная мировая ордината.
	 */
	protected double getMinY() {
		return Double.NaN;
	}

	/**
	 * Максимальная мировая абсцисса.
	 */
	protected double getMaxX() {
		return Double.NaN;
	}

	/**
	 * Максимальная мировая ордината.
	 */
	protected double getMaxY() {
		return Double.NaN;
	}

	/**
	 * Возвращает текст всплывающей подсказки, отображаемой для данного объекта.
	 * 
	 * @desc Вызывает InternalGetHint. Если InternalGetHint не переопределен
	 *       (возваращает false), вызывает функцию Hint объекта, ответственного
	 *       за отрисовку
	 */
	public String getHint() {
		StringBuilder hintStr = new StringBuilder();
		if (internalGetHint(hintStr))
			return hintStr.toString();
		else if (assigned(parent))
			return parent.getHint();
		else {
			return "";
		}
	}

	/**
	 * Собирает объекты и подобъекты, целиком попадающие в прямоугольник.
	 * 
	 * @param aMinX
	 *            левый бок
	 * @param aMinY
	 *            дно
	 * @param aMaxX
	 *            правый бок
	 * @param aMaxY
	 *            крыша
	 * @param addProc
	 *            процедура, собирающая объекты
	 */
	public final void collect(int aMinX, int aMinY, int aMaxX, int aMaxY, Consumer<DiagramObject> addProc) {

		if (isCollectable() && (aMinX < minX()) && (aMaxX > maxX()) && (aMinY < minY()) && (aMaxY > maxY()))
			addProc.accept(this);

		DiagramObject curObj = first;
		while (assigned(curObj)) {
			curObj.collect(aMinX, aMinY, aMaxX, aMaxY, addProc);
			curObj = curObj.getNext();
		}

	}

	/**
	 * Родительский объект, в очереди отрисовки которого находится данный
	 * объект.
	 */
	protected final DiagramObject getParent() {
		return parent;
	}

	/**
	 * Удаляет объект с диаграммы.
	 * 
	 * @desc Во время удаления объект автоматически исключает себя из цепочки.
	 */
	public void erase() {
		if (assigned(previous))
			previous.next = next;
		else if (assigned(parent))
			parent.first = next;
		if (assigned(next))
			next.previous = previous;
		else if (assigned(parent))
			parent.last = previous;
		while (assigned(first)) {
			first.erase();
		}
	}

	/**
	 * Отрисовка объекта.
	 * 
	 * @desc На уровне DrawObject, метод Draw проходит по всей цепочке
	 *       подобъектов и вызывает для них метод Draw
	 * 
	 * @param canvas
	 *            Холст
	 * @param aDX
	 *            смещение по X
	 * @param aDY
	 *            смещение по Y
	 * @param scale
	 *            фактор масштабирования
	 */
	public final void draw(Graphics canvas, double aDX, double aDY, double scale) {
		assert assigned(canvas);
		this.canvas = canvas;
		this.scale = scale;
		dX = aDX;
		dY = aDY;
		saveCanvasSetup();
		internalDraw(canvas);
		restoreCanvasSetup();
		DiagramObject curObj = first;
		while (assigned(curObj)) {
			curObj.draw(canvas, aDX, aDY, scale);
			curObj = curObj.next;
		}
	}

	protected Graphics getCanvas() {
		return canvas;
	}

	/**
	 * Отрисовка выделения объекта.
	 * 
	 * @desc Данный метод выполняет отрисовку аспектов, визуально выделяющих
	 *       выбранный пользователем объект.
	 */
	public final void drawSelection() {
		drawSelection(0, 0);
	}

	/**
	 * Отрисовка выделения объекта со смещением.
	 * 
	 * @param aDX
	 *            экранное смещение по X
	 * @param aDY
	 *            экранное смещение по Y
	 */
	public final void drawSelection(int aDX, int aDY) {
		saveCanvasSetup();
		internalDrawSelection(canvas, aDX, aDY);
		restoreCanvasSetup();
	}

	/**
	 * Перемещение объекта в новую точку.
	 * 
	 * @param aDX
	 *            экранное смещение по X
	 * @param aDY
	 *            экранное смещение по Y
	 * 
	 */
	public final void drop(int aDX, int aDY) {
		internalDrop(aDX / scale, aDY / scale);
	}

	/**
	 * Проверка попадания мышью.
	 * 
	 * @desc Данный метод проходит по цепочке подобъектов DrawObject в порядке,
	 *       обратном порядку отрисовки, и вызывает для них метод TestHit. Если
	 *       какой-либо из вызовов дает результат <> null, метод останавливается
	 *       и возвращает результат. Если после прохода всей цепочки не получен
	 *       результат, отличный от null -- вызывается метод InternalTestHit.
	 * @result Объект, выбранный мышью, или null, если ничего не выбрано
	 * 
	 * @param x
	 *            экранная абсцисса
	 * @param y
	 *            экранная ордината
	 */
	public final DiagramObject testHit(int x, int y) {

		DiagramObject result;
		DiagramObject curObj = last;

		while (assigned(curObj)) {
			result = curObj.testHit(x, y);
			if (assigned(result))
				return result;
			curObj = curObj.previous;
		}
		assert scale > 0;
		if (internalTestHit(x / scale + dX, y / scale + dY))
			result = this;
		else {
			result = null;
		}
		return result;
	}

	/**
	 * Минимальная абсцисса в экранных координатах.
	 */
	public final int minX() {
		double buf = getMinX();
		return Double.isNaN(buf) ? -BIG_VALUE : scaleX(buf);
	}

	/**
	 * Минимальная ордината в экранных координатах.
	 */
	public final int minY() {
		double buf = getMinY();
		return Double.isNaN(buf) ? -BIG_VALUE : scaleY(buf);
	}

	/**
	 * Максимальная абсцисса в экранных координатах.
	 */
	public final int maxX() {
		double buf = getMaxX();
		return Double.isNaN(buf) ? BIG_VALUE : scaleX(buf);
	}

	/**
	 * Максимальная ордината в экранных координатах.
	 */
	public final int maxY() {
		double buf = getMaxY();
		return Double.isNaN(buf) ? BIG_VALUE : scaleY(buf);
	}

	/** Является ли объект перемещаемым с помощью Drag and Drop. */
	public boolean isMoveable() {
		return true;
	}

	/** Можно ли захватить объект с помощью лассо. */
	public boolean isCollectable() {
		return true;
	}

	/** Смещение по оси абсцисс, с которым был отрисован объект. */
	public final double getDX() {
		return dX;
	}

	/** Смещение по оси ординат, с которым был отрисован объект. */
	public final double getDY() {
		return dY;
	}

	/**
	 * Дейстие при наведении мышью. По умолчанию выставляет дефолтный курсор.
	 * 
	 * @param context
	 *            канва, на которой происходит отрисовка.
	 */
	public void mouseOver(Graphics context) {
		// canvas.getCanvas().getStyle().setCursor(Cursor.DEFAULT);
	}

	/**
	 * Дейстие при клике мышью. Поумолчанию ничего не делает.
	 * 
	 * @param y
	 *            ордината клика.
	 * @param x
	 *            абсцисса клика.
	 **/
	public void mouseDown(double x, double y) {
	}

}
