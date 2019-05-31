/*! jQuery v1.8.3 jquery.com | jquery.org/license */
(function (e, t) {
    function _(e) {
        var t = M[e] = {};
        return v.each(e.split(y), function (e, n) {
            t[n] = !0
        }), t
    }

    function H(e, n, r) {
        if (r === t && e.nodeType === 1) {
            var i = "data-" + n.replace(P, "-$1").toLowerCase();
            r = e.getAttribute(i);
            if (typeof r == "string") {
                try {
                    r = r === "true" ? !0 : r === "false" ? !1 : r === "null" ? null : +r + "" === r ? +r : D.test(r) ? v.parseJSON(r) : r
                } catch (s) {
                }
                v.data(e, n, r)
            } else {
                r = t
            }
        }
        return r
    }

    function B(e) {
        var t;
        for (t in e) {
            if (t === "data" && v.isEmptyObject(e[t])) {
                continue
            }
            if (t !== "toJSON") {
                return !1
            }
        }
        return !0
    }

    function et() {
        return !1
    }

    function tt() {
        return !0
    }

    function ut(e) {
        return !e || !e.parentNode || e.parentNode.nodeType === 11
    }

    function at(e, t) {
        do {
            e = e[t]
        } while (e && e.nodeType !== 1);
        return e
    }

    function ft(e, t, n) {
        t = t || 0;
        if (v.isFunction(t)) {
            return v.grep(e, function (e, r) {
                var i = !!t.call(e, r, e);
                return i === n
            })
        }
        if (t.nodeType) {
            return v.grep(e, function (e, r) {
                return e === t === n
            })
        }
        if (typeof t == "string") {
            var r = v.grep(e, function (e) {
                return e.nodeType === 1
            });
            if (it.test(t)) {
                return v.filter(t, r, !n)
            }
            t = v.filter(t, r)
        }
        return v.grep(e, function (e, r) {
            return v.inArray(e, t) >= 0 === n
        })
    }

    function lt(e) {
        var t = ct.split("|"), n = e.createDocumentFragment();
        if (n.createElement) {
            while (t.length) {
                n.createElement(t.pop())
            }
        }
        return n
    }

    function Lt(e, t) {
        return e.getElementsByTagName(t)[0] || e.appendChild(e.ownerDocument.createElement(t))
    }

    function At(e, t) {
        if (t.nodeType !== 1 || !v.hasData(e)) {
            return
        }
        var n, r, i, s = v._data(e), o = v._data(t, s), u = s.events;
        if (u) {
            delete o.handle, o.events = {};
            for (n in u) {
                for (r = 0, i = u[n].length; r < i; r++) {
                    v.event.add(t, n, u[n][r])
                }
            }
        }
        o.data && (o.data = v.extend({}, o.data))
    }

    function Ot(e, t) {
        var n;
        if (t.nodeType !== 1) {
            return
        }
        t.clearAttributes && t.clearAttributes(), t.mergeAttributes && t.mergeAttributes(e), n = t.nodeName.toLowerCase(), n === "object" ? (t.parentNode && (t.outerHTML = e.outerHTML), v.support.html5Clone && e.innerHTML && !v.trim(t.innerHTML) && (t.innerHTML = e.innerHTML)) : n === "input" && Et.test(e.type) ? (t.defaultChecked = t.checked = e.checked, t.value !== e.value && (t.value = e.value)) : n === "option" ? t.selected = e.defaultSelected : n === "input" || n === "textarea" ? t.defaultValue = e.defaultValue : n === "script" && t.text !== e.text && (t.text = e.text), t.removeAttribute(v.expando)
    }

    function Mt(e) {
        return typeof e.getElementsByTagName != "undefined" ? e.getElementsByTagName("*") : typeof e.querySelectorAll != "undefined" ? e.querySelectorAll("*") : []
    }

    function _t(e) {
        Et.test(e.type) && (e.defaultChecked = e.checked)
    }

    function Qt(e, t) {
        if (t in e) {
            return t
        }
        var n = t.charAt(0).toUpperCase() + t.slice(1), r = t, i = Jt.length;
        while (i--) {
            t = Jt[i] + n;
            if (t in e) {
                return t
            }
        }
        return r
    }

    function Gt(e, t) {
        return e = t || e, v.css(e, "display") === "none" || !v.contains(e.ownerDocument, e)
    }

    function Yt(e, t) {
        var n, r, i = [], s = 0, o = e.length;
        for (; s < o; s++) {
            n = e[s];
            if (!n.style) {
                continue
            }
            i[s] = v._data(n, "olddisplay"), t ? (!i[s] && n.style.display === "none" && (n.style.display = ""), n.style.display === "" && Gt(n) && (i[s] = v._data(n, "olddisplay", nn(n.nodeName)))) : (r = Dt(n, "display"), !i[s] && r !== "none" && v._data(n, "olddisplay", r))
        }
        for (s = 0; s < o; s++) {
            n = e[s];
            if (!n.style) {
                continue
            }
            if (!t || n.style.display === "none" || n.style.display === "") {
                n.style.display = t ? i[s] || "" : "none"
            }
        }
        return e
    }

    function Zt(e, t, n) {
        var r = Rt.exec(t);
        return r ? Math.max(0, r[1] - (n || 0)) + (r[2] || "px") : t
    }

    function en(e, t, n, r) {
        var i = n === (r ? "border" : "content") ? 4 : t === "width" ? 1 : 0, s = 0;
        for (; i < 4; i += 2) {
            n === "margin" && (s += v.css(e, n + $t[i], !0)), r ? (n === "content" && (s -= parseFloat(Dt(e, "padding" + $t[i])) || 0), n !== "margin" && (s -= parseFloat(Dt(e, "border" + $t[i] + "Width")) || 0)) : (s += parseFloat(Dt(e, "padding" + $t[i])) || 0, n !== "padding" && (s += parseFloat(Dt(e, "border" + $t[i] + "Width")) || 0))
        }
        return s
    }

    function tn(e, t, n) {
        var r = t === "width" ? e.offsetWidth : e.offsetHeight, i = !0,
            s = v.support.boxSizing && v.css(e, "boxSizing") === "border-box";
        if (r <= 0 || r == null) {
            r = Dt(e, t);
            if (r < 0 || r == null) {
                r = e.style[t]
            }
            if (Ut.test(r)) {
                return r
            }
            i = s && (v.support.boxSizingReliable || r === e.style[t]), r = parseFloat(r) || 0
        }
        return r + en(e, t, n || (s ? "border" : "content"), i) + "px"
    }

    function nn(e) {
        if (Wt[e]) {
            return Wt[e]
        }
        var t = v("<" + e + ">").appendTo(i.body), n = t.css("display");
        t.remove();
        if (n === "none" || n === "") {
            Pt = i.body.appendChild(Pt || v.extend(i.createElement("iframe"), {frameBorder: 0, width: 0, height: 0}));
            if (!Ht || !Pt.createElement) {
                Ht = (Pt.contentWindow || Pt.contentDocument).document, Ht.write("<!doctype html><html><body>"), Ht.close()
            }
            t = Ht.body.appendChild(Ht.createElement(e)), n = Dt(t, "display"), i.body.removeChild(Pt)
        }
        return Wt[e] = n, n
    }

    function fn(e, t, n, r) {
        var i;
        if (v.isArray(t)) {
            v.each(t, function (t, i) {
                n || sn.test(e) ? r(e, i) : fn(e + "[" + (typeof i == "object" ? t : "") + "]", i, n, r)
            })
        } else {
            if (!n && v.type(t) === "object") {
                for (i in t) {
                    fn(e + "[" + i + "]", t[i], n, r)
                }
            } else {
                r(e, t)
            }
        }
    }

    function Cn(e) {
        return function (t, n) {
            typeof t != "string" && (n = t, t = "*");
            var r, i, s, o = t.toLowerCase().split(y), u = 0, a = o.length;
            if (v.isFunction(n)) {
                for (; u < a; u++) {
                    r = o[u], s = /^\+/.test(r), s && (r = r.substr(1) || "*"), i = e[r] = e[r] || [], i[s ? "unshift" : "push"](n)
                }
            }
        }
    }

    function kn(e, n, r, i, s, o) {
        s = s || n.dataTypes[0], o = o || {}, o[s] = !0;
        var u, a = e[s], f = 0, l = a ? a.length : 0, c = e === Sn;
        for (; f < l && (c || !u); f++) {
            u = a[f](n, r, i), typeof u == "string" && (!c || o[u] ? u = t : (n.dataTypes.unshift(u), u = kn(e, n, r, i, u, o)))
        }
        return (c || !u) && !o["*"] && (u = kn(e, n, r, i, "*", o)), u
    }

    function Ln(e, n) {
        var r, i, s = v.ajaxSettings.flatOptions || {};
        for (r in n) {
            n[r] !== t && ((s[r] ? e : i || (i = {}))[r] = n[r])
        }
        i && v.extend(!0, e, i)
    }

    function An(e, n, r) {
        var i, s, o, u, a = e.contents, f = e.dataTypes, l = e.responseFields;
        for (s in l) {
            s in r && (n[l[s]] = r[s])
        }
        while (f[0] === "*") {
            f.shift(), i === t && (i = e.mimeType || n.getResponseHeader("content-type"))
        }
        if (i) {
            for (s in a) {
                if (a[s] && a[s].test(i)) {
                    f.unshift(s);
                    break
                }
            }
        }
        if (f[0] in r) {
            o = f[0]
        } else {
            for (s in r) {
                if (!f[0] || e.converters[s + " " + f[0]]) {
                    o = s;
                    break
                }
                u || (u = s)
            }
            o = o || u
        }
        if (o) {
            return o !== f[0] && f.unshift(o), r[o]
        }
    }

    function On(e, t) {
        var n, r, i, s, o = e.dataTypes.slice(), u = o[0], a = {}, f = 0;
        e.dataFilter && (t = e.dataFilter(t, e.dataType));
        if (o[1]) {
            for (n in e.converters) {
                a[n.toLowerCase()] = e.converters[n]
            }
        }
        for (; i = o[++f];) {
            if (i !== "*") {
                if (u !== "*" && u !== i) {
                    n = a[u + " " + i] || a["* " + i];
                    if (!n) {
                        for (r in a) {
                            s = r.split(" ");
                            if (s[1] === i) {
                                n = a[u + " " + s[0]] || a["* " + s[0]];
                                if (n) {
                                    n === !0 ? n = a[r] : a[r] !== !0 && (i = s[0], o.splice(f--, 0, i));
                                    break
                                }
                            }
                        }
                    }
                    if (n !== !0) {
                        if (n && e["throws"]) {
                            t = n(t)
                        } else {
                            try {
                                t = n(t)
                            } catch (l) {
                                return {state: "parsererror", error: n ? l : "No conversion from " + u + " to " + i}
                            }
                        }
                    }
                }
                u = i
            }
        }
        return {state: "success", data: t}
    }

    function Fn() {
        try {
            return new e.XMLHttpRequest
        } catch (t) {
        }
    }

    function In() {
        try {
            return new e.ActiveXObject("Microsoft.XMLHTTP")
        } catch (t) {
        }
    }

    function $n() {
        return setTimeout(function () {
            qn = t
        }, 0), qn = v.now()
    }

    function Jn(e, t) {
        v.each(t, function (t, n) {
            var r = (Vn[t] || []).concat(Vn["*"]), i = 0, s = r.length;
            for (; i < s; i++) {
                if (r[i].call(e, t, n)) {
                    return
                }
            }
        })
    }

    function Kn(e, t, n) {
        var r, i = 0, s = 0, o = Xn.length, u = v.Deferred().always(function () {
            delete a.elem
        }), a = function () {
            var t = qn || $n(), n = Math.max(0, f.startTime + f.duration - t), r = n / f.duration || 0, i = 1 - r,
                s = 0, o = f.tweens.length;
            for (; s < o; s++) {
                f.tweens[s].run(i)
            }
            return u.notifyWith(e, [f, i, n]), i < 1 && o ? n : (u.resolveWith(e, [f]), !1)
        }, f = u.promise({
            elem: e,
            props: v.extend({}, t),
            opts: v.extend(!0, {specialEasing: {}}, n),
            originalProperties: t,
            originalOptions: n,
            startTime: qn || $n(),
            duration: n.duration,
            tweens: [],
            createTween: function (t, n, r) {
                var i = v.Tween(e, f.opts, t, n, f.opts.specialEasing[t] || f.opts.easing);
                return f.tweens.push(i), i
            },
            stop: function (t) {
                var n = 0, r = t ? f.tweens.length : 0;
                for (; n < r; n++) {
                    f.tweens[n].run(1)
                }
                return t ? u.resolveWith(e, [f, t]) : u.rejectWith(e, [f, t]), this
            }
        }), l = f.props;
        Qn(l, f.opts.specialEasing);
        for (; i < o; i++) {
            r = Xn[i].call(f, e, l, f.opts);
            if (r) {
                return r
            }
        }
        return Jn(f, l), v.isFunction(f.opts.start) && f.opts.start.call(e, f), v.fx.timer(v.extend(a, {
            anim: f,
            queue: f.opts.queue,
            elem: e
        })), f.progress(f.opts.progress).done(f.opts.done, f.opts.complete).fail(f.opts.fail).always(f.opts.always)
    }

    function Qn(e, t) {
        var n, r, i, s, o;
        for (n in e) {
            r = v.camelCase(n), i = t[r], s = e[n], v.isArray(s) && (i = s[1], s = e[n] = s[0]), n !== r && (e[r] = s, delete e[n]), o = v.cssHooks[r];
            if (o && "expand" in o) {
                s = o.expand(s), delete e[r];
                for (n in s) {
                    n in e || (e[n] = s[n], t[n] = i)
                }
            } else {
                t[r] = i
            }
        }
    }

    function Gn(e, t, n) {
        var r, i, s, o, u, a, f, l, c, h = this, p = e.style, d = {}, m = [], g = e.nodeType && Gt(e);
        n.queue || (l = v._queueHooks(e, "fx"), l.unqueued == null && (l.unqueued = 0, c = l.empty.fire, l.empty.fire = function () {
            l.unqueued || c()
        }), l.unqueued++, h.always(function () {
            h.always(function () {
                l.unqueued--, v.queue(e, "fx").length || l.empty.fire()
            })
        })), e.nodeType === 1 && ("height" in t || "width" in t) && (n.overflow = [p.overflow, p.overflowX, p.overflowY], v.css(e, "display") === "inline" && v.css(e, "float") === "none" && (!v.support.inlineBlockNeedsLayout || nn(e.nodeName) === "inline" ? p.display = "inline-block" : p.zoom = 1)), n.overflow && (p.overflow = "hidden", v.support.shrinkWrapBlocks || h.done(function () {
            p.overflow = n.overflow[0], p.overflowX = n.overflow[1], p.overflowY = n.overflow[2]
        }));
        for (r in t) {
            s = t[r];
            if (Un.exec(s)) {
                delete t[r], a = a || s === "toggle";
                if (s === (g ? "hide" : "show")) {
                    continue
                }
                m.push(r)
            }
        }
        o = m.length;
        if (o) {
            u = v._data(e, "fxshow") || v._data(e, "fxshow", {}), "hidden" in u && (g = u.hidden), a && (u.hidden = !g), g ? v(e).show() : h.done(function () {
                v(e).hide()
            }), h.done(function () {
                var t;
                v.removeData(e, "fxshow", !0);
                for (t in d) {
                    v.style(e, t, d[t])
                }
            });
            for (r = 0; r < o; r++) {
                i = m[r], f = h.createTween(i, g ? u[i] : 0), d[i] = u[i] || v.style(e, i), i in u || (u[i] = f.start, g && (f.end = f.start, f.start = i === "width" || i === "height" ? 1 : 0))
            }
        }
    }

    function Yn(e, t, n, r, i) {
        return new Yn.prototype.init(e, t, n, r, i)
    }

    function Zn(e, t) {
        var n, r = {height: e}, i = 0;
        t = t ? 1 : 0;
        for (; i < 4; i += 2 - t) {
            n = $t[i], r["margin" + n] = r["padding" + n] = e
        }
        return t && (r.opacity = r.width = e), r
    }

    function tr(e) {
        return v.isWindow(e) ? e : e.nodeType === 9 ? e.defaultView || e.parentWindow : !1
    }

    var n, r, i = e.document, s = e.location, o = e.navigator, u = e.jQuery, a = e.$, f = Array.prototype.push,
        l = Array.prototype.slice, c = Array.prototype.indexOf, h = Object.prototype.toString,
        p = Object.prototype.hasOwnProperty, d = String.prototype.trim, v = function (e, t) {
            return new v.fn.init(e, t, n)
        }, m = /[\-+]?(?:\d*\.|)\d+(?:[eE][\-+]?\d+|)/.source, g = /\S/, y = /\s+/,
        b = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, w = /^(?:[^#<]*(<[\w\W]+>)[^>]*$|#([\w\-]*)$)/,
        E = /^<(\w+)\s*\/?>(?:<\/\1>|)$/, S = /^[\],:{}\s]*$/, x = /(?:^|:|,)(?:\s*\[)+/g,
        T = /\\(?:["\\\/bfnrt]|u[\da-fA-F]{4})/g,
        N = /"[^"\\\r\n]*"|true|false|null|-?(?:\d\d*\.|)\d+(?:[eE][\-+]?\d+|)/g, C = /^-ms-/, k = /-([\da-z])/gi,
        L = function (e, t) {
            return (t + "").toUpperCase()
        }, A = function () {
            i.addEventListener ? (i.removeEventListener("DOMContentLoaded", A, !1), v.ready()) : i.readyState === "complete" && (i.detachEvent("onreadystatechange", A), v.ready())
        }, O = {};
    v.fn = v.prototype = {
        constructor: v, init: function (e, n, r) {
            var s, o, u, a;
            if (!e) {
                return this
            }
            if (e.nodeType) {
                return this.context = this[0] = e, this.length = 1, this
            }
            if (typeof e == "string") {
                e.charAt(0) === "<" && e.charAt(e.length - 1) === ">" && e.length >= 3 ? s = [null, e, null] : s = w.exec(e);
                if (s && (s[1] || !n)) {
                    if (s[1]) {
                        return n = n instanceof v ? n[0] : n, a = n && n.nodeType ? n.ownerDocument || n : i, e = v.parseHTML(s[1], a, !0), E.test(s[1]) && v.isPlainObject(n) && this.attr.call(e, n, !0), v.merge(this, e)
                    }
                    o = i.getElementById(s[2]);
                    if (o && o.parentNode) {
                        if (o.id !== s[2]) {
                            return r.find(e)
                        }
                        this.length = 1, this[0] = o
                    }
                    return this.context = i, this.selector = e, this
                }
                return !n || n.jquery ? (n || r).find(e) : this.constructor(n).find(e)
            }
            return v.isFunction(e) ? r.ready(e) : (e.selector !== t && (this.selector = e.selector, this.context = e.context), v.makeArray(e, this))
        }, selector: "", jquery: "1.8.3", length: 0, size: function () {
            return this.length
        }, toArray: function () {
            return l.call(this)
        }, get: function (e) {
            return e == null ? this.toArray() : e < 0 ? this[this.length + e] : this[e]
        }, pushStack: function (e, t, n) {
            var r = v.merge(this.constructor(), e);
            return r.prevObject = this, r.context = this.context, t === "find" ? r.selector = this.selector + (this.selector ? " " : "") + n : t && (r.selector = this.selector + "." + t + "(" + n + ")"), r
        }, each: function (e, t) {
            return v.each(this, e, t)
        }, ready: function (e) {
            return v.ready.promise().done(e), this
        }, eq: function (e) {
            return e = +e, e === -1 ? this.slice(e) : this.slice(e, e + 1)
        }, first: function () {
            return this.eq(0)
        }, last: function () {
            return this.eq(-1)
        }, slice: function () {
            return this.pushStack(l.apply(this, arguments), "slice", l.call(arguments).join(","))
        }, map: function (e) {
            return this.pushStack(v.map(this, function (t, n) {
                return e.call(t, n, t)
            }))
        }, end: function () {
            return this.prevObject || this.constructor(null)
        }, push: f, sort: [].sort, splice: [].splice
    }, v.fn.init.prototype = v.fn, v.extend = v.fn.extend = function () {
        var e, n, r, i, s, o, u = arguments[0] || {}, a = 1, f = arguments.length, l = !1;
        typeof u == "boolean" && (l = u, u = arguments[1] || {}, a = 2), typeof u != "object" && !v.isFunction(u) && (u = {}), f === a && (u = this, --a);
        for (; a < f; a++) {
            if ((e = arguments[a]) != null) {
                for (n in e) {
                    r = u[n], i = e[n];
                    if (u === i) {
                        continue
                    }
                    l && i && (v.isPlainObject(i) || (s = v.isArray(i))) ? (s ? (s = !1, o = r && v.isArray(r) ? r : []) : o = r && v.isPlainObject(r) ? r : {}, u[n] = v.extend(l, o, i)) : i !== t && (u[n] = i)
                }
            }
        }
        return u
    }, v.extend({
        noConflict: function (t) {
            return e.$ === v && (e.$ = a), t && e.jQuery === v && (e.jQuery = u), v
        }, isReady: !1, readyWait: 1, holdReady: function (e) {
            e ? v.readyWait++ : v.ready(!0)
        }, ready: function (e) {
            if (e === !0 ? --v.readyWait : v.isReady) {
                return
            }
            if (!i.body) {
                return setTimeout(v.ready, 1)
            }
            v.isReady = !0;
            if (e !== !0 && --v.readyWait > 0) {
                return
            }
            r.resolveWith(i, [v]), v.fn.trigger && v(i).trigger("ready").off("ready")
        }, isFunction: function (e) {
            return v.type(e) === "function"
        }, isArray: Array.isArray || function (e) {
            return v.type(e) === "array"
        }, isWindow: function (e) {
            return e != null && e == e.window
        }, isNumeric: function (e) {
            return !isNaN(parseFloat(e)) && isFinite(e)
        }, type: function (e) {
            return e == null ? String(e) : O[h.call(e)] || "object"
        }, isPlainObject: function (e) {
            if (!e || v.type(e) !== "object" || e.nodeType || v.isWindow(e)) {
                return !1
            }
            try {
                if (e.constructor && !p.call(e, "constructor") && !p.call(e.constructor.prototype, "isPrototypeOf")) {
                    return !1
                }
            } catch (n) {
                return !1
            }
            var r;
            for (r in e) {
            }
            return r === t || p.call(e, r)
        }, isEmptyObject: function (e) {
            var t;
            for (t in e) {
                return !1
            }
            return !0
        }, error: function (e) {
            throw new Error(e)
        }, parseHTML: function (e, t, n) {
            var r;
            return !e || typeof e != "string" ? null : (typeof t == "boolean" && (n = t, t = 0), t = t || i, (r = E.exec(e)) ? [t.createElement(r[1])] : (r = v.buildFragment([e], t, n ? null : []), v.merge([], (r.cacheable ? v.clone(r.fragment) : r.fragment).childNodes)))
        }, parseJSON: function (t) {
            if (!t || typeof t != "string") {
                return null
            }
            t = v.trim(t);
            if (e.JSON && e.JSON.parse) {
                return e.JSON.parse(t)
            }
            if (S.test(t.replace(T, "@").replace(N, "]").replace(x, ""))) {
                return (new Function("return " + t))()
            }
            v.error("Invalid JSON: " + t)
        }, parseXML: function (n) {
            var r, i;
            if (!n || typeof n != "string") {
                return null
            }
            try {
                e.DOMParser ? (i = new DOMParser, r = i.parseFromString(n, "text/xml")) : (r = new ActiveXObject("Microsoft.XMLDOM"), r.async = "false", r.loadXML(n))
            } catch (s) {
                r = t
            }
            return (!r || !r.documentElement || r.getElementsByTagName("parsererror").length) && v.error("Invalid XML: " + n), r
        }, noop: function () {
        }, globalEval: function (t) {
            t && g.test(t) && (e.execScript || function (t) {
                e.eval.call(e, t)
            })(t)
        }, camelCase: function (e) {
            return e.replace(C, "ms-").replace(k, L)
        }, nodeName: function (e, t) {
            return e.nodeName && e.nodeName.toLowerCase() === t.toLowerCase()
        }, each: function (e, n, r) {
            var i, s = 0, o = e.length, u = o === t || v.isFunction(e);
            if (r) {
                if (u) {
                    for (i in e) {
                        if (n.apply(e[i], r) === !1) {
                            break
                        }
                    }
                } else {
                    for (; s < o;) {
                        if (n.apply(e[s++], r) === !1) {
                            break
                        }
                    }
                }
            } else {
                if (u) {
                    for (i in e) {
                        if (n.call(e[i], i, e[i]) === !1) {
                            break
                        }
                    }
                } else {
                    for (; s < o;) {
                        if (n.call(e[s], s, e[s++]) === !1) {
                            break
                        }
                    }
                }
            }
            return e
        }, trim: d && !d.call("\ufeff\u00a0") ? function (e) {
            return e == null ? "" : d.call(e)
        } : function (e) {
            return e == null ? "" : (e + "").replace(b, "")
        }, makeArray: function (e, t) {
            var n, r = t || [];
            return e != null && (n = v.type(e), e.length == null || n === "string" || n === "function" || n === "regexp" || v.isWindow(e) ? f.call(r, e) : v.merge(r, e)), r
        }, inArray: function (e, t, n) {
            var r;
            if (t) {
                if (c) {
                    return c.call(t, e, n)
                }
                r = t.length, n = n ? n < 0 ? Math.max(0, r + n) : n : 0;
                for (; n < r; n++) {
                    if (n in t && t[n] === e) {
                        return n
                    }
                }
            }
            return -1
        }, merge: function (e, n) {
            var r = n.length, i = e.length, s = 0;
            if (typeof r == "number") {
                for (; s < r; s++) {
                    e[i++] = n[s]
                }
            } else {
                while (n[s] !== t) {
                    e[i++] = n[s++]
                }
            }
            return e.length = i, e
        }, grep: function (e, t, n) {
            var r, i = [], s = 0, o = e.length;
            n = !!n;
            for (; s < o; s++) {
                r = !!t(e[s], s), n !== r && i.push(e[s])
            }
            return i
        }, map: function (e, n, r) {
            var i, s, o = [], u = 0, a = e.length,
                f = e instanceof v || a !== t && typeof a == "number" && (a > 0 && e[0] && e[a - 1] || a === 0 || v.isArray(e));
            if (f) {
                for (; u < a; u++) {
                    i = n(e[u], u, r), i != null && (o[o.length] = i)
                }
            } else {
                for (s in e) {
                    i = n(e[s], s, r), i != null && (o[o.length] = i)
                }
            }
            return o.concat.apply([], o)
        }, guid: 1, proxy: function (e, n) {
            var r, i, s;
            return typeof n == "string" && (r = e[n], n = e, e = r), v.isFunction(e) ? (i = l.call(arguments, 2), s = function () {
                return e.apply(n, i.concat(l.call(arguments)))
            }, s.guid = e.guid = e.guid || v.guid++, s) : t
        }, access: function (e, n, r, i, s, o, u) {
            var a, f = r == null, l = 0, c = e.length;
            if (r && typeof r == "object") {
                for (l in r) {
                    v.access(e, n, l, r[l], 1, o, i)
                }
                s = 1
            } else {
                if (i !== t) {
                    a = u === t && v.isFunction(i), f && (a ? (a = n, n = function (e, t, n) {
                        return a.call(v(e), n)
                    }) : (n.call(e, i), n = null));
                    if (n) {
                        for (; l < c; l++) {
                            n(e[l], r, a ? i.call(e[l], l, n(e[l], r)) : i, u)
                        }
                    }
                    s = 1
                }
            }
            return s ? e : f ? n.call(e) : c ? n(e[0], r) : o
        }, now: function () {
            return (new Date).getTime()
        }
    }), v.ready.promise = function (t) {
        if (!r) {
            r = v.Deferred();
            if (i.readyState === "complete") {
                setTimeout(v.ready, 1)
            } else {
                if (i.addEventListener) {
                    i.addEventListener("DOMContentLoaded", A, !1), e.addEventListener("load", v.ready, !1)
                } else {
                    i.attachEvent("onreadystatechange", A), e.attachEvent("onload", v.ready);
                    var n = !1;
                    try {
                        n = e.frameElement == null && i.documentElement
                    } catch (s) {
                    }
                    n && n.doScroll && function o() {
                        if (!v.isReady) {
                            try {
                                n.doScroll("left")
                            } catch (e) {
                                return setTimeout(o, 50)
                            }
                            v.ready()
                        }
                    }()
                }
            }
        }
        return r.promise(t)
    }, v.each("Boolean Number String Function Array Date RegExp Object".split(" "), function (e, t) {
        O["[object " + t + "]"] = t.toLowerCase()
    }), n = v(i);
    var M = {};
    v.Callbacks = function (e) {
        e = typeof e == "string" ? M[e] || _(e) : v.extend({}, e);
        var n, r, i, s, o, u, a = [], f = !e.once && [], l = function (t) {
            n = e.memory && t, r = !0, u = s || 0, s = 0, o = a.length, i = !0;
            for (; a && u < o; u++) {
                if (a[u].apply(t[0], t[1]) === !1 && e.stopOnFalse) {
                    n = !1;
                    break
                }
            }
            i = !1, a && (f ? f.length && l(f.shift()) : n ? a = [] : c.disable())
        }, c = {
            add: function () {
                if (a) {
                    var t = a.length;
                    (function r(t) {
                        v.each(t, function (t, n) {
                            var i = v.type(n);
                            i === "function" ? (!e.unique || !c.has(n)) && a.push(n) : n && n.length && i !== "string" && r(n)
                        })
                    })(arguments), i ? o = a.length : n && (s = t, l(n))
                }
                return this
            }, remove: function () {
                return a && v.each(arguments, function (e, t) {
                    var n;
                    while ((n = v.inArray(t, a, n)) > -1) {
                        a.splice(n, 1), i && (n <= o && o--, n <= u && u--)
                    }
                }), this
            }, has: function (e) {
                return v.inArray(e, a) > -1
            }, empty: function () {
                return a = [], this
            }, disable: function () {
                return a = f = n = t, this
            }, disabled: function () {
                return !a
            }, lock: function () {
                return f = t, n || c.disable(), this
            }, locked: function () {
                return !f
            }, fireWith: function (e, t) {
                return t = t || [], t = [e, t.slice ? t.slice() : t], a && (!r || f) && (i ? f.push(t) : l(t)), this
            }, fire: function () {
                return c.fireWith(this, arguments), this
            }, fired: function () {
                return !!r
            }
        };
        return c
    }, v.extend({
        Deferred: function (e) {
            var t = [["resolve", "done", v.Callbacks("once memory"), "resolved"], ["reject", "fail", v.Callbacks("once memory"), "rejected"], ["notify", "progress", v.Callbacks("memory")]],
                n = "pending", r = {
                    state: function () {
                        return n
                    }, always: function () {
                        return i.done(arguments).fail(arguments), this
                    }, then: function () {
                        var e = arguments;
                        return v.Deferred(function (n) {
                            v.each(t, function (t, r) {
                                var s = r[0], o = e[t];
                                i[r[1]](v.isFunction(o) ? function () {
                                    var e = o.apply(this, arguments);
                                    e && v.isFunction(e.promise) ? e.promise().done(n.resolve).fail(n.reject).progress(n.notify) : n[s + "With"](this === i ? n : this, [e])
                                } : n[s])
                            }), e = null
                        }).promise()
                    }, promise: function (e) {
                        return e != null ? v.extend(e, r) : r
                    }
                }, i = {};
            return r.pipe = r.then, v.each(t, function (e, s) {
                var o = s[2], u = s[3];
                r[s[1]] = o.add, u && o.add(function () {
                    n = u
                }, t[e ^ 1][2].disable, t[2][2].lock), i[s[0]] = o.fire, i[s[0] + "With"] = o.fireWith
            }), r.promise(i), e && e.call(i, i), i
        }, when: function (e) {
            var t = 0, n = l.call(arguments), r = n.length, i = r !== 1 || e && v.isFunction(e.promise) ? r : 0,
                s = i === 1 ? e : v.Deferred(), o = function (e, t, n) {
                    return function (r) {
                        t[e] = this, n[e] = arguments.length > 1 ? l.call(arguments) : r, n === u ? s.notifyWith(t, n) : --i || s.resolveWith(t, n)
                    }
                }, u, a, f;
            if (r > 1) {
                u = new Array(r), a = new Array(r), f = new Array(r);
                for (; t < r; t++) {
                    n[t] && v.isFunction(n[t].promise) ? n[t].promise().done(o(t, f, n)).fail(s.reject).progress(o(t, a, u)) : --i
                }
            }
            return i || s.resolveWith(f, n), s.promise()
        }
    }), v.support = function () {
        var t, n, r, s, o, u, a, f, l, c, h, p = i.createElement("div");
        p.setAttribute("className", "t"), p.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>", n = p.getElementsByTagName("*"), r = p.getElementsByTagName("a")[0];
        if (!n || !r || !n.length) {
            return {}
        }
        s = i.createElement("select"), o = s.appendChild(i.createElement("option")), u = p.getElementsByTagName("input")[0], r.style.cssText = "top:1px;float:left;opacity:.5", t = {
            leadingWhitespace: p.firstChild.nodeType === 3,
            tbody: !p.getElementsByTagName("tbody").length,
            htmlSerialize: !!p.getElementsByTagName("link").length,
            style: /top/.test(r.getAttribute("style")),
            hrefNormalized: r.getAttribute("href") === "/a",
            opacity: /^0.5/.test(r.style.opacity),
            cssFloat: !!r.style.cssFloat,
            checkOn: u.value === "on",
            optSelected: o.selected,
            getSetAttribute: p.className !== "t",
            enctype: !!i.createElement("form").enctype,
            html5Clone: i.createElement("nav").cloneNode(!0).outerHTML !== "<:nav></:nav>",
            boxModel: i.compatMode === "CSS1Compat",
            submitBubbles: !0,
            changeBubbles: !0,
            focusinBubbles: !1,
            deleteExpando: !0,
            noCloneEvent: !0,
            inlineBlockNeedsLayout: !1,
            shrinkWrapBlocks: !1,
            reliableMarginRight: !0,
            boxSizingReliable: !0,
            pixelPosition: !1
        }, u.checked = !0, t.noCloneChecked = u.cloneNode(!0).checked, s.disabled = !0, t.optDisabled = !o.disabled;
        try {
            delete p.test
        } catch (d) {
            t.deleteExpando = !1
        }
        !p.addEventListener && p.attachEvent && p.fireEvent && (p.attachEvent("onclick", h = function () {
            t.noCloneEvent = !1
        }), p.cloneNode(!0).fireEvent("onclick"), p.detachEvent("onclick", h)), u = i.createElement("input"), u.value = "t", u.setAttribute("type", "radio"), t.radioValue = u.value === "t", u.setAttribute("checked", "checked"), u.setAttribute("name", "t"), p.appendChild(u), a = i.createDocumentFragment(), a.appendChild(p.lastChild), t.checkClone = a.cloneNode(!0).cloneNode(!0).lastChild.checked, t.appendChecked = u.checked, a.removeChild(u), a.appendChild(p);
        if (p.attachEvent) {
            for (l in {submit: !0, change: !0, focusin: !0}) {
                f = "on" + l, c = f in p, c || (p.setAttribute(f, "return;"), c = typeof p[f] == "function"), t[l + "Bubbles"] = c
            }
        }
        return v(function () {
            var n, r, s, o, u = "padding:0;margin:0;border:0;display:block;overflow:hidden;",
                a = i.getElementsByTagName("body")[0];
            if (!a) {
                return
            }
            n = i.createElement("div"), n.style.cssText = "visibility:hidden;border:0;width:0;height:0;position:static;top:0;margin-top:1px", a.insertBefore(n, a.firstChild), r = i.createElement("div"), n.appendChild(r), r.innerHTML = "<table><tr><td></td><td>t</td></tr></table>", s = r.getElementsByTagName("td"), s[0].style.cssText = "padding:0;margin:0;border:0;display:none", c = s[0].offsetHeight === 0, s[0].style.display = "", s[1].style.display = "none", t.reliableHiddenOffsets = c && s[0].offsetHeight === 0, r.innerHTML = "", r.style.cssText = "box-sizing:border-box;-moz-box-sizing:border-box;-webkit-box-sizing:border-box;padding:1px;border:1px;display:block;width:4px;margin-top:1%;position:absolute;top:1%;", t.boxSizing = r.offsetWidth === 4, t.doesNotIncludeMarginInBodyOffset = a.offsetTop !== 1, e.getComputedStyle && (t.pixelPosition = (e.getComputedStyle(r, null) || {}).top !== "1%", t.boxSizingReliable = (e.getComputedStyle(r, null) || {width: "4px"}).width === "4px", o = i.createElement("div"), o.style.cssText = r.style.cssText = u, o.style.marginRight = o.style.width = "0", r.style.width = "1px", r.appendChild(o), t.reliableMarginRight = !parseFloat((e.getComputedStyle(o, null) || {}).marginRight)), typeof r.style.zoom != "undefined" && (r.innerHTML = "", r.style.cssText = u + "width:1px;padding:1px;display:inline;zoom:1", t.inlineBlockNeedsLayout = r.offsetWidth === 3, r.style.display = "block", r.style.overflow = "visible", r.innerHTML = "<div></div>", r.firstChild.style.width = "5px", t.shrinkWrapBlocks = r.offsetWidth !== 3, n.style.zoom = 1), a.removeChild(n), n = r = s = o = null
        }), a.removeChild(p), n = r = s = o = u = a = p = null, t
    }();
    var D = /(?:\{[\s\S]*\}|\[[\s\S]*\])$/, P = /([A-Z])/g;
    v.extend({
        cache: {},
        deletedIds: [],
        uuid: 0,
        expando: "jQuery" + (v.fn.jquery + Math.random()).replace(/\D/g, ""),
        noData: {embed: !0, object: "clsid:D27CDB6E-AE6D-11cf-96B8-444553540000", applet: !0},
        hasData: function (e) {
            return e = e.nodeType ? v.cache[e[v.expando]] : e[v.expando], !!e && !B(e)
        },
        data: function (e, n, r, i) {
            if (!v.acceptData(e)) {
                return
            }
            var s, o, u = v.expando, a = typeof n == "string", f = e.nodeType, l = f ? v.cache : e,
                c = f ? e[u] : e[u] && u;
            if ((!c || !l[c] || !i && !l[c].data) && a && r === t) {
                return
            }
            c || (f ? e[u] = c = v.deletedIds.pop() || v.guid++ : c = u), l[c] || (l[c] = {}, f || (l[c].toJSON = v.noop));
            if (typeof n == "object" || typeof n == "function") {
                i ? l[c] = v.extend(l[c], n) : l[c].data = v.extend(l[c].data, n)
            }
            return s = l[c], i || (s.data || (s.data = {}), s = s.data), r !== t && (s[v.camelCase(n)] = r), a ? (o = s[n], o == null && (o = s[v.camelCase(n)])) : o = s, o
        },
        removeData: function (e, t, n) {
            if (!v.acceptData(e)) {
                return
            }
            var r, i, s, o = e.nodeType, u = o ? v.cache : e, a = o ? e[v.expando] : v.expando;
            if (!u[a]) {
                return
            }
            if (t) {
                r = n ? u[a] : u[a].data;
                if (r) {
                    v.isArray(t) || (t in r ? t = [t] : (t = v.camelCase(t), t in r ? t = [t] : t = t.split(" ")));
                    for (i = 0, s = t.length; i < s; i++) {
                        delete r[t[i]]
                    }
                    if (!(n ? B : v.isEmptyObject)(r)) {
                        return
                    }
                }
            }
            if (!n) {
                delete u[a].data;
                if (!B(u[a])) {
                    return
                }
            }
            o ? v.cleanData([e], !0) : v.support.deleteExpando || u != u.window ? delete u[a] : u[a] = null
        },
        _data: function (e, t, n) {
            return v.data(e, t, n, !0)
        },
        acceptData: function (e) {
            var t = e.nodeName && v.noData[e.nodeName.toLowerCase()];
            return !t || t !== !0 && e.getAttribute("classid") === t
        }
    }), v.fn.extend({
        data: function (e, n) {
            var r, i, s, o, u, a = this[0], f = 0, l = null;
            if (e === t) {
                if (this.length) {
                    l = v.data(a);
                    if (a.nodeType === 1 && !v._data(a, "parsedAttrs")) {
                        s = a.attributes;
                        for (u = s.length; f < u; f++) {
                            o = s[f].name, o.indexOf("data-") || (o = v.camelCase(o.substring(5)), H(a, o, l[o]))
                        }
                        v._data(a, "parsedAttrs", !0)
                    }
                }
                return l
            }
            return typeof e == "object" ? this.each(function () {
                v.data(this, e)
            }) : (r = e.split(".", 2), r[1] = r[1] ? "." + r[1] : "", i = r[1] + "!", v.access(this, function (n) {
                if (n === t) {
                    return l = this.triggerHandler("getData" + i, [r[0]]), l === t && a && (l = v.data(a, e), l = H(a, e, l)), l === t && r[1] ? this.data(r[0]) : l
                }
                r[1] = n, this.each(function () {
                    var t = v(this);
                    t.triggerHandler("setData" + i, r), v.data(this, e, n), t.triggerHandler("changeData" + i, r)
                })
            }, null, n, arguments.length > 1, null, !1))
        }, removeData: function (e) {
            return this.each(function () {
                v.removeData(this, e)
            })
        }
    }), v.extend({
        queue: function (e, t, n) {
            var r;
            if (e) {
                return t = (t || "fx") + "queue", r = v._data(e, t), n && (!r || v.isArray(n) ? r = v._data(e, t, v.makeArray(n)) : r.push(n)), r || []
            }
        }, dequeue: function (e, t) {
            t = t || "fx";
            var n = v.queue(e, t), r = n.length, i = n.shift(), s = v._queueHooks(e, t), o = function () {
                v.dequeue(e, t)
            };
            i === "inprogress" && (i = n.shift(), r--), i && (t === "fx" && n.unshift("inprogress"), delete s.stop, i.call(e, o, s)), !r && s && s.empty.fire()
        }, _queueHooks: function (e, t) {
            var n = t + "queueHooks";
            return v._data(e, n) || v._data(e, n, {
                empty: v.Callbacks("once memory").add(function () {
                    v.removeData(e, t + "queue", !0), v.removeData(e, n, !0)
                })
            })
        }
    }), v.fn.extend({
        queue: function (e, n) {
            var r = 2;
            return typeof e != "string" && (n = e, e = "fx", r--), arguments.length < r ? v.queue(this[0], e) : n === t ? this : this.each(function () {
                var t = v.queue(this, e, n);
                v._queueHooks(this, e), e === "fx" && t[0] !== "inprogress" && v.dequeue(this, e)
            })
        }, dequeue: function (e) {
            return this.each(function () {
                v.dequeue(this, e)
            })
        }, delay: function (e, t) {
            return e = v.fx ? v.fx.speeds[e] || e : e, t = t || "fx", this.queue(t, function (t, n) {
                var r = setTimeout(t, e);
                n.stop = function () {
                    clearTimeout(r)
                }
            })
        }, clearQueue: function (e) {
            return this.queue(e || "fx", [])
        }, promise: function (e, n) {
            var r, i = 1, s = v.Deferred(), o = this, u = this.length, a = function () {
                --i || s.resolveWith(o, [o])
            };
            typeof e != "string" && (n = e, e = t), e = e || "fx";
            while (u--) {
                r = v._data(o[u], e + "queueHooks"), r && r.empty && (i++, r.empty.add(a))
            }
            return a(), s.promise(n)
        }
    });
    var j, F, I, q = /[\t\r\n]/g, R = /\r/g, U = /^(?:button|input)$/i,
        z = /^(?:button|input|object|select|textarea)$/i, W = /^a(?:rea|)$/i,
        X = /^(?:autofocus|autoplay|async|checked|controls|defer|disabled|hidden|loop|multiple|open|readonly|required|scoped|selected)$/i,
        V = v.support.getSetAttribute;
    v.fn.extend({
        attr: function (e, t) {
            return v.access(this, v.attr, e, t, arguments.length > 1)
        }, removeAttr: function (e) {
            return this.each(function () {
                v.removeAttr(this, e)
            })
        }, prop: function (e, t) {
            return v.access(this, v.prop, e, t, arguments.length > 1)
        }, removeProp: function (e) {
            return e = v.propFix[e] || e, this.each(function () {
                try {
                    this[e] = t, delete this[e]
                } catch (n) {
                }
            })
        }, addClass: function (e) {
            var t, n, r, i, s, o, u;
            if (v.isFunction(e)) {
                return this.each(function (t) {
                    v(this).addClass(e.call(this, t, this.className))
                })
            }
            if (e && typeof e == "string") {
                t = e.split(y);
                for (n = 0, r = this.length; n < r; n++) {
                    i = this[n];
                    if (i.nodeType === 1) {
                        if (!i.className && t.length === 1) {
                            i.className = e
                        } else {
                            s = " " + i.className + " ";
                            for (o = 0, u = t.length; o < u; o++) {
                                s.indexOf(" " + t[o] + " ") < 0 && (s += t[o] + " ")
                            }
                            i.className = v.trim(s)
                        }
                    }
                }
            }
            return this
        }, removeClass: function (e) {
            var n, r, i, s, o, u, a;
            if (v.isFunction(e)) {
                return this.each(function (t) {
                    v(this).removeClass(e.call(this, t, this.className))
                })
            }
            if (e && typeof e == "string" || e === t) {
                n = (e || "").split(y);
                for (u = 0, a = this.length; u < a; u++) {
                    i = this[u];
                    if (i.nodeType === 1 && i.className) {
                        r = (" " + i.className + " ").replace(q, " ");
                        for (s = 0, o = n.length; s < o; s++) {
                            while (r.indexOf(" " + n[s] + " ") >= 0) {
                                r = r.replace(" " + n[s] + " ", " ")
                            }
                        }
                        i.className = e ? v.trim(r) : ""
                    }
                }
            }
            return this
        }, toggleClass: function (e, t) {
            var n = typeof e, r = typeof t == "boolean";
            return v.isFunction(e) ? this.each(function (n) {
                v(this).toggleClass(e.call(this, n, this.className, t), t)
            }) : this.each(function () {
                if (n === "string") {
                    var i, s = 0, o = v(this), u = t, a = e.split(y);
                    while (i = a[s++]) {
                        u = r ? u : !o.hasClass(i), o[u ? "addClass" : "removeClass"](i)
                    }
                } else {
                    if (n === "undefined" || n === "boolean") {
                        this.className && v._data(this, "__className__", this.className), this.className = this.className || e === !1 ? "" : v._data(this, "__className__") || ""
                    }
                }
            })
        }, hasClass: function (e) {
            var t = " " + e + " ", n = 0, r = this.length;
            for (; n < r; n++) {
                if (this[n].nodeType === 1 && (" " + this[n].className + " ").replace(q, " ").indexOf(t) >= 0) {
                    return !0
                }
            }
            return !1
        }, val: function (e) {
            var n, r, i, s = this[0];
            if (!arguments.length) {
                if (s) {
                    return n = v.valHooks[s.type] || v.valHooks[s.nodeName.toLowerCase()], n && "get" in n && (r = n.get(s, "value")) !== t ? r : (r = s.value, typeof r == "string" ? r.replace(R, "") : r == null ? "" : r)
                }
                return
            }
            return i = v.isFunction(e), this.each(function (r) {
                var s, o = v(this);
                if (this.nodeType !== 1) {
                    return
                }
                i ? s = e.call(this, r, o.val()) : s = e, s == null ? s = "" : typeof s == "number" ? s += "" : v.isArray(s) && (s = v.map(s, function (e) {
                    return e == null ? "" : e + ""
                })), n = v.valHooks[this.type] || v.valHooks[this.nodeName.toLowerCase()];
                if (!n || !("set" in n) || n.set(this, s, "value") === t) {
                    this.value = s
                }
            })
        }
    }), v.extend({
        valHooks: {
            option: {
                get: function (e) {
                    var t = e.attributes.value;
                    return !t || t.specified ? e.value : e.text
                }
            }, select: {
                get: function (e) {
                    var t, n, r = e.options, i = e.selectedIndex, s = e.type === "select-one" || i < 0,
                        o = s ? null : [], u = s ? i + 1 : r.length, a = i < 0 ? u : s ? i : 0;
                    for (; a < u; a++) {
                        n = r[a];
                        if ((n.selected || a === i) && (v.support.optDisabled ? !n.disabled : n.getAttribute("disabled") === null) && (!n.parentNode.disabled || !v.nodeName(n.parentNode, "optgroup"))) {
                            t = v(n).val();
                            if (s) {
                                return t
                            }
                            o.push(t)
                        }
                    }
                    return o
                }, set: function (e, t) {
                    var n = v.makeArray(t);
                    return v(e).find("option").each(function () {
                        this.selected = v.inArray(v(this).val(), n) >= 0
                    }), n.length || (e.selectedIndex = -1), n
                }
            }
        },
        attrFn: {},
        attr: function (e, n, r, i) {
            var s, o, u, a = e.nodeType;
            if (!e || a === 3 || a === 8 || a === 2) {
                return
            }
            if (i && v.isFunction(v.fn[n])) {
                return v(e)[n](r)
            }
            if (typeof e.getAttribute == "undefined") {
                return v.prop(e, n, r)
            }
            u = a !== 1 || !v.isXMLDoc(e), u && (n = n.toLowerCase(), o = v.attrHooks[n] || (X.test(n) ? F : j));
            if (r !== t) {
                if (r === null) {
                    v.removeAttr(e, n);
                    return
                }
                return o && "set" in o && u && (s = o.set(e, r, n)) !== t ? s : (e.setAttribute(n, r + ""), r)
            }
            return o && "get" in o && u && (s = o.get(e, n)) !== null ? s : (s = e.getAttribute(n), s === null ? t : s)
        },
        removeAttr: function (e, t) {
            var n, r, i, s, o = 0;
            if (t && e.nodeType === 1) {
                r = t.split(y);
                for (; o < r.length; o++) {
                    i = r[o], i && (n = v.propFix[i] || i, s = X.test(i), s || v.attr(e, i, ""), e.removeAttribute(V ? i : n), s && n in e && (e[n] = !1))
                }
            }
        },
        attrHooks: {
            type: {
                set: function (e, t) {
                    if (U.test(e.nodeName) && e.parentNode) {
                        v.error("type property can't be changed")
                    } else {
                        if (!v.support.radioValue && t === "radio" && v.nodeName(e, "input")) {
                            var n = e.value;
                            return e.setAttribute("type", t), n && (e.value = n), t
                        }
                    }
                }
            }, value: {
                get: function (e, t) {
                    return j && v.nodeName(e, "button") ? j.get(e, t) : t in e ? e.value : null
                }, set: function (e, t, n) {
                    if (j && v.nodeName(e, "button")) {
                        return j.set(e, t, n)
                    }
                    e.value = t
                }
            }
        },
        propFix: {
            tabindex: "tabIndex",
            readonly: "readOnly",
            "for": "htmlFor",
            "class": "className",
            maxlength: "maxLength",
            cellspacing: "cellSpacing",
            cellpadding: "cellPadding",
            rowspan: "rowSpan",
            colspan: "colSpan",
            usemap: "useMap",
            frameborder: "frameBorder",
            contenteditable: "contentEditable"
        },
        prop: function (e, n, r) {
            var i, s, o, u = e.nodeType;
            if (!e || u === 3 || u === 8 || u === 2) {
                return
            }
            return o = u !== 1 || !v.isXMLDoc(e), o && (n = v.propFix[n] || n, s = v.propHooks[n]), r !== t ? s && "set" in s && (i = s.set(e, r, n)) !== t ? i : e[n] = r : s && "get" in s && (i = s.get(e, n)) !== null ? i : e[n]
        },
        propHooks: {
            tabIndex: {
                get: function (e) {
                    var n = e.getAttributeNode("tabindex");
                    return n && n.specified ? parseInt(n.value, 10) : z.test(e.nodeName) || W.test(e.nodeName) && e.href ? 0 : t
                }
            }
        }
    }), F = {
        get: function (e, n) {
            var r, i = v.prop(e, n);
            return i === !0 || typeof i != "boolean" && (r = e.getAttributeNode(n)) && r.nodeValue !== !1 ? n.toLowerCase() : t
        }, set: function (e, t, n) {
            var r;
            return t === !1 ? v.removeAttr(e, n) : (r = v.propFix[n] || n, r in e && (e[r] = !0), e.setAttribute(n, n.toLowerCase())), n
        }
    }, V || (I = {name: !0, id: !0, coords: !0}, j = v.valHooks.button = {
        get: function (e, n) {
            var r;
            return r = e.getAttributeNode(n), r && (I[n] ? r.value !== "" : r.specified) ? r.value : t
        }, set: function (e, t, n) {
            var r = e.getAttributeNode(n);
            return r || (r = i.createAttribute(n), e.setAttributeNode(r)), r.value = t + ""
        }
    }, v.each(["width", "height"], function (e, t) {
        v.attrHooks[t] = v.extend(v.attrHooks[t], {
            set: function (e, n) {
                if (n === "") {
                    return e.setAttribute(t, "auto"), n
                }
            }
        })
    }), v.attrHooks.contenteditable = {
        get: j.get, set: function (e, t, n) {
            t === "" && (t = "false"), j.set(e, t, n)
        }
    }), v.support.hrefNormalized || v.each(["href", "src", "width", "height"], function (e, n) {
        v.attrHooks[n] = v.extend(v.attrHooks[n], {
            get: function (e) {
                var r = e.getAttribute(n, 2);
                return r === null ? t : r
            }
        })
    }), v.support.style || (v.attrHooks.style = {
        get: function (e) {
            return e.style.cssText.toLowerCase() || t
        }, set: function (e, t) {
            return e.style.cssText = t + ""
        }
    }), v.support.optSelected || (v.propHooks.selected = v.extend(v.propHooks.selected, {
        get: function (e) {
            var t = e.parentNode;
            return t && (t.selectedIndex, t.parentNode && t.parentNode.selectedIndex), null
        }
    })), v.support.enctype || (v.propFix.enctype = "encoding"), v.support.checkOn || v.each(["radio", "checkbox"], function () {
        v.valHooks[this] = {
            get: function (e) {
                return e.getAttribute("value") === null ? "on" : e.value
            }
        }
    }), v.each(["radio", "checkbox"], function () {
        v.valHooks[this] = v.extend(v.valHooks[this], {
            set: function (e, t) {
                if (v.isArray(t)) {
                    return e.checked = v.inArray(v(e).val(), t) >= 0
                }
            }
        })
    });
    var $ = /^(?:textarea|input|select)$/i, J = /^([^\.]*|)(?:\.(.+)|)$/, K = /(?:^|\s)hover(\.\S+|)\b/, Q = /^key/,
        G = /^(?:mouse|contextmenu)|click/, Y = /^(?:focusinfocus|focusoutblur)$/, Z = function (e) {
            return v.event.special.hover ? e : e.replace(K, "mouseenter$1 mouseleave$1")
        };
    v.event = {
        add: function (e, n, r, i, s) {
            var o, u, a, f, l, c, h, p, d, m, g;
            if (e.nodeType === 3 || e.nodeType === 8 || !n || !r || !(o = v._data(e))) {
                return
            }
            r.handler && (d = r, r = d.handler, s = d.selector), r.guid || (r.guid = v.guid++), a = o.events, a || (o.events = a = {}), u = o.handle, u || (o.handle = u = function (e) {
                return typeof v == "undefined" || !!e && v.event.triggered === e.type ? t : v.event.dispatch.apply(u.elem, arguments)
            }, u.elem = e), n = v.trim(Z(n)).split(" ");
            for (f = 0; f < n.length; f++) {
                l = J.exec(n[f]) || [], c = l[1], h = (l[2] || "").split(".").sort(), g = v.event.special[c] || {}, c = (s ? g.delegateType : g.bindType) || c, g = v.event.special[c] || {}, p = v.extend({
                    type: c,
                    origType: l[1],
                    data: i,
                    handler: r,
                    guid: r.guid,
                    selector: s,
                    needsContext: s && v.expr.match.needsContext.test(s),
                    namespace: h.join(".")
                }, d), m = a[c];
                if (!m) {
                    m = a[c] = [], m.delegateCount = 0;
                    if (!g.setup || g.setup.call(e, i, h, u) === !1) {
                        e.addEventListener ? e.addEventListener(c, u, !1) : e.attachEvent && e.attachEvent("on" + c, u)
                    }
                }
                g.add && (g.add.call(e, p), p.handler.guid || (p.handler.guid = r.guid)), s ? m.splice(m.delegateCount++, 0, p) : m.push(p), v.event.global[c] = !0
            }
            e = null
        },
        global: {},
        remove: function (e, t, n, r, i) {
            var s, o, u, a, f, l, c, h, p, d, m, g = v.hasData(e) && v._data(e);
            if (!g || !(h = g.events)) {
                return
            }
            t = v.trim(Z(t || "")).split(" ");
            for (s = 0; s < t.length; s++) {
                o = J.exec(t[s]) || [], u = a = o[1], f = o[2];
                if (!u) {
                    for (u in h) {
                        v.event.remove(e, u + t[s], n, r, !0)
                    }
                    continue
                }
                p = v.event.special[u] || {}, u = (r ? p.delegateType : p.bindType) || u, d = h[u] || [], l = d.length, f = f ? new RegExp("(^|\\.)" + f.split(".").sort().join("\\.(?:.*\\.|)") + "(\\.|$)") : null;
                for (c = 0; c < d.length; c++) {
                    m = d[c], (i || a === m.origType) && (!n || n.guid === m.guid) && (!f || f.test(m.namespace)) && (!r || r === m.selector || r === "**" && m.selector) && (d.splice(c--, 1), m.selector && d.delegateCount--, p.remove && p.remove.call(e, m))
                }
                d.length === 0 && l !== d.length && ((!p.teardown || p.teardown.call(e, f, g.handle) === !1) && v.removeEvent(e, u, g.handle), delete h[u])
            }
            v.isEmptyObject(h) && (delete g.handle, v.removeData(e, "events", !0))
        },
        customEvent: {getData: !0, setData: !0, changeData: !0},
        trigger: function (n, r, s, o) {
            if (!s || s.nodeType !== 3 && s.nodeType !== 8) {
                var u, a, f, l, c, h, p, d, m, g, y = n.type || n, b = [];
                if (Y.test(y + v.event.triggered)) {
                    return
                }
                y.indexOf("!") >= 0 && (y = y.slice(0, -1), a = !0), y.indexOf(".") >= 0 && (b = y.split("."), y = b.shift(), b.sort());
                if ((!s || v.event.customEvent[y]) && !v.event.global[y]) {
                    return
                }
                n = typeof n == "object" ? n[v.expando] ? n : new v.Event(y, n) : new v.Event(y), n.type = y, n.isTrigger = !0, n.exclusive = a, n.namespace = b.join("."), n.namespace_re = n.namespace ? new RegExp("(^|\\.)" + b.join("\\.(?:.*\\.|)") + "(\\.|$)") : null, h = y.indexOf(":") < 0 ? "on" + y : "";
                if (!s) {
                    u = v.cache;
                    for (f in u) {
                        u[f].events && u[f].events[y] && v.event.trigger(n, r, u[f].handle.elem, !0)
                    }
                    return
                }
                n.result = t, n.target || (n.target = s), r = r != null ? v.makeArray(r) : [], r.unshift(n), p = v.event.special[y] || {};
                if (p.trigger && p.trigger.apply(s, r) === !1) {
                    return
                }
                m = [[s, p.bindType || y]];
                if (!o && !p.noBubble && !v.isWindow(s)) {
                    g = p.delegateType || y, l = Y.test(g + y) ? s : s.parentNode;
                    for (c = s; l; l = l.parentNode) {
                        m.push([l, g]), c = l
                    }
                    c === (s.ownerDocument || i) && m.push([c.defaultView || c.parentWindow || e, g])
                }
                for (f = 0; f < m.length && !n.isPropagationStopped(); f++) {
                    l = m[f][0], n.type = m[f][1], d = (v._data(l, "events") || {})[n.type] && v._data(l, "handle"), d && d.apply(l, r), d = h && l[h], d && v.acceptData(l) && d.apply && d.apply(l, r) === !1 && n.preventDefault()
                }
                return n.type = y, !o && !n.isDefaultPrevented() && (!p._default || p._default.apply(s.ownerDocument, r) === !1) && (y !== "click" || !v.nodeName(s, "a")) && v.acceptData(s) && h && s[y] && (y !== "focus" && y !== "blur" || n.target.offsetWidth !== 0) && !v.isWindow(s) && (c = s[h], c && (s[h] = null), v.event.triggered = y, s[y](), v.event.triggered = t, c && (s[h] = c)), n.result
            }
            return
        },
        dispatch: function (n) {
            n = v.event.fix(n || e.event);
            var r, i, s, o, u, a, f, c, h, p, d = (v._data(this, "events") || {})[n.type] || [], m = d.delegateCount,
                g = l.call(arguments), y = !n.exclusive && !n.namespace, b = v.event.special[n.type] || {}, w = [];
            g[0] = n, n.delegateTarget = this;
            if (b.preDispatch && b.preDispatch.call(this, n) === !1) {
                return
            }
            if (m && (!n.button || n.type !== "click")) {
                for (s = n.target; s != this; s = s.parentNode || this) {
                    if (s.disabled !== !0 || n.type !== "click") {
                        u = {}, f = [];
                        for (r = 0; r < m; r++) {
                            c = d[r], h = c.selector, u[h] === t && (u[h] = c.needsContext ? v(h, this).index(s) >= 0 : v.find(h, this, null, [s]).length), u[h] && f.push(c)
                        }
                        f.length && w.push({elem: s, matches: f})
                    }
                }
            }
            d.length > m && w.push({elem: this, matches: d.slice(m)});
            for (r = 0; r < w.length && !n.isPropagationStopped(); r++) {
                a = w[r], n.currentTarget = a.elem;
                for (i = 0; i < a.matches.length && !n.isImmediatePropagationStopped(); i++) {
                    c = a.matches[i];
                    if (y || !n.namespace && !c.namespace || n.namespace_re && n.namespace_re.test(c.namespace)) {
                        n.data = c.data, n.handleObj = c, o = ((v.event.special[c.origType] || {}).handle || c.handler).apply(a.elem, g), o !== t && (n.result = o, o === !1 && (n.preventDefault(), n.stopPropagation()))
                    }
                }
            }
            return b.postDispatch && b.postDispatch.call(this, n), n.result
        },
        props: "attrChange attrName relatedNode srcElement altKey bubbles cancelable ctrlKey currentTarget eventPhase metaKey relatedTarget shiftKey target timeStamp view which".split(" "),
        fixHooks: {},
        keyHooks: {
            props: "char charCode key keyCode".split(" "), filter: function (e, t) {
                return e.which == null && (e.which = t.charCode != null ? t.charCode : t.keyCode), e
            }
        },
        mouseHooks: {
            props: "button buttons clientX clientY fromElement offsetX offsetY pageX pageY screenX screenY toElement".split(" "),
            filter: function (e, n) {
                var r, s, o, u = n.button, a = n.fromElement;
                return e.pageX == null && n.clientX != null && (r = e.target.ownerDocument || i, s = r.documentElement, o = r.body, e.pageX = n.clientX + (s && s.scrollLeft || o && o.scrollLeft || 0) - (s && s.clientLeft || o && o.clientLeft || 0), e.pageY = n.clientY + (s && s.scrollTop || o && o.scrollTop || 0) - (s && s.clientTop || o && o.clientTop || 0)), !e.relatedTarget && a && (e.relatedTarget = a === e.target ? n.toElement : a), !e.which && u !== t && (e.which = u & 1 ? 1 : u & 2 ? 3 : u & 4 ? 2 : 0), e
            }
        },
        fix: function (e) {
            if (e[v.expando]) {
                return e
            }
            var t, n, r = e, s = v.event.fixHooks[e.type] || {}, o = s.props ? this.props.concat(s.props) : this.props;
            e = v.Event(r);
            for (t = o.length; t;) {
                n = o[--t], e[n] = r[n]
            }
            return e.target || (e.target = r.srcElement || i), e.target.nodeType === 3 && (e.target = e.target.parentNode), e.metaKey = !!e.metaKey, s.filter ? s.filter(e, r) : e
        },
        special: {
            load: {noBubble: !0},
            focus: {delegateType: "focusin"},
            blur: {delegateType: "focusout"},
            beforeunload: {
                setup: function (e, t, n) {
                    v.isWindow(this) && (this.onbeforeunload = n)
                }, teardown: function (e, t) {
                    this.onbeforeunload === t && (this.onbeforeunload = null)
                }
            }
        },
        simulate: function (e, t, n, r) {
            var i = v.extend(new v.Event, n, {type: e, isSimulated: !0, originalEvent: {}});
            r ? v.event.trigger(i, null, t) : v.event.dispatch.call(t, i), i.isDefaultPrevented() && n.preventDefault()
        }
    }, v.event.handle = v.event.dispatch, v.removeEvent = i.removeEventListener ? function (e, t, n) {
        e.removeEventListener && e.removeEventListener(t, n, !1)
    } : function (e, t, n) {
        var r = "on" + t;
        e.detachEvent && (typeof e[r] == "undefined" && (e[r] = null), e.detachEvent(r, n))
    }, v.Event = function (e, t) {
        if (!(this instanceof v.Event)) {
            return new v.Event(e, t)
        }
        e && e.type ? (this.originalEvent = e, this.type = e.type, this.isDefaultPrevented = e.defaultPrevented || e.returnValue === !1 || e.getPreventDefault && e.getPreventDefault() ? tt : et) : this.type = e, t && v.extend(this, t), this.timeStamp = e && e.timeStamp || v.now(), this[v.expando] = !0
    }, v.Event.prototype = {
        preventDefault: function () {
            this.isDefaultPrevented = tt;
            var e = this.originalEvent;
            if (!e) {
                return
            }
            e.preventDefault ? e.preventDefault() : e.returnValue = !1
        }, stopPropagation: function () {
            this.isPropagationStopped = tt;
            var e = this.originalEvent;
            if (!e) {
                return
            }
            e.stopPropagation && e.stopPropagation(), e.cancelBubble = !0
        }, stopImmediatePropagation: function () {
            this.isImmediatePropagationStopped = tt, this.stopPropagation()
        }, isDefaultPrevented: et, isPropagationStopped: et, isImmediatePropagationStopped: et
    }, v.each({mouseenter: "mouseover", mouseleave: "mouseout"}, function (e, t) {
        v.event.special[e] = {
            delegateType: t, bindType: t, handle: function (e) {
                var n, r = this, i = e.relatedTarget, s = e.handleObj, o = s.selector;
                if (!i || i !== r && !v.contains(r, i)) {
                    e.type = s.origType, n = s.handler.apply(this, arguments), e.type = t
                }
                return n
            }
        }
    }), v.support.submitBubbles || (v.event.special.submit = {
        setup: function () {
            if (v.nodeName(this, "form")) {
                return !1
            }
            v.event.add(this, "click._submit keypress._submit", function (e) {
                var n = e.target, r = v.nodeName(n, "input") || v.nodeName(n, "button") ? n.form : t;
                r && !v._data(r, "_submit_attached") && (v.event.add(r, "submit._submit", function (e) {
                    e._submit_bubble = !0
                }), v._data(r, "_submit_attached", !0))
            })
        }, postDispatch: function (e) {
            e._submit_bubble && (delete e._submit_bubble, this.parentNode && !e.isTrigger && v.event.simulate("submit", this.parentNode, e, !0))
        }, teardown: function () {
            if (v.nodeName(this, "form")) {
                return !1
            }
            v.event.remove(this, "._submit")
        }
    }), v.support.changeBubbles || (v.event.special.change = {
        setup: function () {
            if ($.test(this.nodeName)) {
                if (this.type === "checkbox" || this.type === "radio") {
                    v.event.add(this, "propertychange._change", function (e) {
                        e.originalEvent.propertyName === "checked" && (this._just_changed = !0)
                    }), v.event.add(this, "click._change", function (e) {
                        this._just_changed && !e.isTrigger && (this._just_changed = !1), v.event.simulate("change", this, e, !0)
                    })
                }
                return !1
            }
            v.event.add(this, "beforeactivate._change", function (e) {
                var t = e.target;
                $.test(t.nodeName) && !v._data(t, "_change_attached") && (v.event.add(t, "change._change", function (e) {
                    this.parentNode && !e.isSimulated && !e.isTrigger && v.event.simulate("change", this.parentNode, e, !0)
                }), v._data(t, "_change_attached", !0))
            })
        }, handle: function (e) {
            var t = e.target;
            if (this !== t || e.isSimulated || e.isTrigger || t.type !== "radio" && t.type !== "checkbox") {
                return e.handleObj.handler.apply(this, arguments)
            }
        }, teardown: function () {
            return v.event.remove(this, "._change"), !$.test(this.nodeName)
        }
    }), v.support.focusinBubbles || v.each({focus: "focusin", blur: "focusout"}, function (e, t) {
        var n = 0, r = function (e) {
            v.event.simulate(t, e.target, v.event.fix(e), !0)
        };
        v.event.special[t] = {
            setup: function () {
                n++ === 0 && i.addEventListener(e, r, !0)
            }, teardown: function () {
                --n === 0 && i.removeEventListener(e, r, !0)
            }
        }
    }), v.fn.extend({
        on: function (e, n, r, i, s) {
            var o, u;
            if (typeof e == "object") {
                typeof n != "string" && (r = r || n, n = t);
                for (u in e) {
                    this.on(u, n, r, e[u], s)
                }
                return this
            }
            r == null && i == null ? (i = n, r = n = t) : i == null && (typeof n == "string" ? (i = r, r = t) : (i = r, r = n, n = t));
            if (i === !1) {
                i = et
            } else {
                if (!i) {
                    return this
                }
            }
            return s === 1 && (o = i, i = function (e) {
                return v().off(e), o.apply(this, arguments)
            }, i.guid = o.guid || (o.guid = v.guid++)), this.each(function () {
                v.event.add(this, e, i, r, n)
            })
        }, one: function (e, t, n, r) {
            return this.on(e, t, n, r, 1)
        }, off: function (e, n, r) {
            var i, s;
            if (e && e.preventDefault && e.handleObj) {
                return i = e.handleObj, v(e.delegateTarget).off(i.namespace ? i.origType + "." + i.namespace : i.origType, i.selector, i.handler), this
            }
            if (typeof e == "object") {
                for (s in e) {
                    this.off(s, n, e[s])
                }
                return this
            }
            if (n === !1 || typeof n == "function") {
                r = n, n = t
            }
            return r === !1 && (r = et), this.each(function () {
                v.event.remove(this, e, r, n)
            })
        }, bind: function (e, t, n) {
            return this.on(e, null, t, n)
        }, unbind: function (e, t) {
            return this.off(e, null, t)
        }, live: function (e, t, n) {
            return v(this.context).on(e, this.selector, t, n), this
        }, die: function (e, t) {
            return v(this.context).off(e, this.selector || "**", t), this
        }, delegate: function (e, t, n, r) {
            return this.on(t, e, n, r)
        }, undelegate: function (e, t, n) {
            return arguments.length === 1 ? this.off(e, "**") : this.off(t, e || "**", n)
        }, trigger: function (e, t) {
            return this.each(function () {
                v.event.trigger(e, t, this)
            })
        }, triggerHandler: function (e, t) {
            if (this[0]) {
                return v.event.trigger(e, t, this[0], !0)
            }
        }, toggle: function (e) {
            var t = arguments, n = e.guid || v.guid++, r = 0, i = function (n) {
                var i = (v._data(this, "lastToggle" + e.guid) || 0) % r;
                return v._data(this, "lastToggle" + e.guid, i + 1), n.preventDefault(), t[i].apply(this, arguments) || !1
            };
            i.guid = n;
            while (r < t.length) {
                t[r++].guid = n
            }
            return this.click(i)
        }, hover: function (e, t) {
            return this.mouseenter(e).mouseleave(t || e)
        }
    }), v.each("blur focus focusin focusout load resize scroll unload click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select submit keydown keypress keyup error contextmenu".split(" "), function (e, t) {
        v.fn[t] = function (e, n) {
            return n == null && (n = e, e = null), arguments.length > 0 ? this.on(t, null, e, n) : this.trigger(t)
        }, Q.test(t) && (v.event.fixHooks[t] = v.event.keyHooks), G.test(t) && (v.event.fixHooks[t] = v.event.mouseHooks)
    }), function (e, t) {
        function nt(e, t, n, r) {
            n = n || [], t = t || g;
            var i, s, a, f, l = t.nodeType;
            if (!e || typeof e != "string") {
                return n
            }
            if (l !== 1 && l !== 9) {
                return []
            }
            a = o(t);
            if (!a && !r) {
                if (i = R.exec(e)) {
                    if (f = i[1]) {
                        if (l === 9) {
                            s = t.getElementById(f);
                            if (!s || !s.parentNode) {
                                return n
                            }
                            if (s.id === f) {
                                return n.push(s), n
                            }
                        } else {
                            if (t.ownerDocument && (s = t.ownerDocument.getElementById(f)) && u(t, s) && s.id === f) {
                                return n.push(s), n
                            }
                        }
                    } else {
                        if (i[2]) {
                            return S.apply(n, x.call(t.getElementsByTagName(e), 0)), n
                        }
                        if ((f = i[3]) && Z && t.getElementsByClassName) {
                            return S.apply(n, x.call(t.getElementsByClassName(f), 0)), n
                        }
                    }
                }
            }
            return vt(e.replace(j, "$1"), t, n, r, a)
        }

        function rt(e) {
            return function (t) {
                var n = t.nodeName.toLowerCase();
                return n === "input" && t.type === e
            }
        }

        function it(e) {
            return function (t) {
                var n = t.nodeName.toLowerCase();
                return (n === "input" || n === "button") && t.type === e
            }
        }

        function st(e) {
            return N(function (t) {
                return t = +t, N(function (n, r) {
                    var i, s = e([], n.length, t), o = s.length;
                    while (o--) {
                        n[i = s[o]] && (n[i] = !(r[i] = n[i]))
                    }
                })
            })
        }

        function ot(e, t, n) {
            if (e === t) {
                return n
            }
            var r = e.nextSibling;
            while (r) {
                if (r === t) {
                    return -1
                }
                r = r.nextSibling
            }
            return 1
        }

        function ut(e, t) {
            var n, r, s, o, u, a, f, l = L[d][e + " "];
            if (l) {
                return t ? 0 : l.slice(0)
            }
            u = e, a = [], f = i.preFilter;
            while (u) {
                if (!n || (r = F.exec(u))) {
                    r && (u = u.slice(r[0].length) || u), a.push(s = [])
                }
                n = !1;
                if (r = I.exec(u)) {
                    s.push(n = new m(r.shift())), u = u.slice(n.length), n.type = r[0].replace(j, " ")
                }
                for (o in i.filter) {
                    (r = J[o].exec(u)) && (!f[o] || (r = f[o](r))) && (s.push(n = new m(r.shift())), u = u.slice(n.length), n.type = o, n.matches = r)
                }
                if (!n) {
                    break
                }
            }
            return t ? u.length : u ? nt.error(e) : L(e, a).slice(0)
        }

        function at(e, t, r) {
            var i = t.dir, s = r && t.dir === "parentNode", o = w++;
            return t.first ? function (t, n, r) {
                while (t = t[i]) {
                    if (s || t.nodeType === 1) {
                        return e(t, n, r)
                    }
                }
            } : function (t, r, u) {
                if (!u) {
                    var a, f = b + " " + o + " ", l = f + n;
                    while (t = t[i]) {
                        if (s || t.nodeType === 1) {
                            if ((a = t[d]) === l) {
                                return t.sizset
                            }
                            if (typeof a == "string" && a.indexOf(f) === 0) {
                                if (t.sizset) {
                                    return t
                                }
                            } else {
                                t[d] = l;
                                if (e(t, r, u)) {
                                    return t.sizset = !0, t
                                }
                                t.sizset = !1
                            }
                        }
                    }
                } else {
                    while (t = t[i]) {
                        if (s || t.nodeType === 1) {
                            if (e(t, r, u)) {
                                return t
                            }
                        }
                    }
                }
            }
        }

        function ft(e) {
            return e.length > 1 ? function (t, n, r) {
                var i = e.length;
                while (i--) {
                    if (!e[i](t, n, r)) {
                        return !1
                    }
                }
                return !0
            } : e[0]
        }

        function lt(e, t, n, r, i) {
            var s, o = [], u = 0, a = e.length, f = t != null;
            for (; u < a; u++) {
                if (s = e[u]) {
                    if (!n || n(s, r, i)) {
                        o.push(s), f && t.push(u)
                    }
                }
            }
            return o
        }

        function ct(e, t, n, r, i, s) {
            return r && !r[d] && (r = ct(r)), i && !i[d] && (i = ct(i, s)), N(function (s, o, u, a) {
                var f, l, c, h = [], p = [], d = o.length, v = s || dt(t || "*", u.nodeType ? [u] : u, []),
                    m = e && (s || !t) ? lt(v, h, e, u, a) : v, g = n ? i || (s ? e : d || r) ? [] : o : m;
                n && n(m, g, u, a);
                if (r) {
                    f = lt(g, p), r(f, [], u, a), l = f.length;
                    while (l--) {
                        if (c = f[l]) {
                            g[p[l]] = !(m[p[l]] = c)
                        }
                    }
                }
                if (s) {
                    if (i || e) {
                        if (i) {
                            f = [], l = g.length;
                            while (l--) {
                                (c = g[l]) && f.push(m[l] = c)
                            }
                            i(null, g = [], f, a)
                        }
                        l = g.length;
                        while (l--) {
                            (c = g[l]) && (f = i ? T.call(s, c) : h[l]) > -1 && (s[f] = !(o[f] = c))
                        }
                    }
                } else {
                    g = lt(g === o ? g.splice(d, g.length) : g), i ? i(null, o, g, a) : S.apply(o, g)
                }
            })
        }

        function ht(e) {
            var t, n, r, s = e.length, o = i.relative[e[0].type], u = o || i.relative[" "], a = o ? 1 : 0,
                f = at(function (e) {
                    return e === t
                }, u, !0), l = at(function (e) {
                    return T.call(t, e) > -1
                }, u, !0), h = [function (e, n, r) {
                    return !o && (r || n !== c) || ((t = n).nodeType ? f(e, n, r) : l(e, n, r))
                }];
            for (; a < s; a++) {
                if (n = i.relative[e[a].type]) {
                    h = [at(ft(h), n)]
                } else {
                    n = i.filter[e[a].type].apply(null, e[a].matches);
                    if (n[d]) {
                        r = ++a;
                        for (; r < s; r++) {
                            if (i.relative[e[r].type]) {
                                break
                            }
                        }
                        return ct(a > 1 && ft(h), a > 1 && e.slice(0, a - 1).join("").replace(j, "$1"), n, a < r && ht(e.slice(a, r)), r < s && ht(e = e.slice(r)), r < s && e.join(""))
                    }
                    h.push(n)
                }
            }
            return ft(h)
        }

        function pt(e, t) {
            var r = t.length > 0, s = e.length > 0, o = function (u, a, f, l, h) {
                var p, d, v, m = [], y = 0, w = "0", x = u && [], T = h != null, N = c,
                    C = u || s && i.find.TAG("*", h && a.parentNode || a), k = b += N == null ? 1 : Math.E;
                T && (c = a !== g && a, n = o.el);
                for (; (p = C[w]) != null; w++) {
                    if (s && p) {
                        for (d = 0; v = e[d]; d++) {
                            if (v(p, a, f)) {
                                l.push(p);
                                break
                            }
                        }
                        T && (b = k, n = ++o.el)
                    }
                    r && ((p = !v && p) && y--, u && x.push(p))
                }
                y += w;
                if (r && w !== y) {
                    for (d = 0; v = t[d]; d++) {
                        v(x, m, a, f)
                    }
                    if (u) {
                        if (y > 0) {
                            while (w--) {
                                !x[w] && !m[w] && (m[w] = E.call(l))
                            }
                        }
                        m = lt(m)
                    }
                    S.apply(l, m), T && !u && m.length > 0 && y + t.length > 1 && nt.uniqueSort(l)
                }
                return T && (b = k, c = N), x
            };
            return o.el = 0, r ? N(o) : o
        }

        function dt(e, t, n) {
            var r = 0, i = t.length;
            for (; r < i; r++) {
                nt(e, t[r], n)
            }
            return n
        }

        function vt(e, t, n, r, s) {
            var o, u, f, l, c, h = ut(e), p = h.length;
            if (!r && h.length === 1) {
                u = h[0] = h[0].slice(0);
                if (u.length > 2 && (f = u[0]).type === "ID" && t.nodeType === 9 && !s && i.relative[u[1].type]) {
                    t = i.find.ID(f.matches[0].replace($, ""), t, s)[0];
                    if (!t) {
                        return n
                    }
                    e = e.slice(u.shift().length)
                }
                for (o = J.POS.test(e) ? -1 : u.length - 1; o >= 0; o--) {
                    f = u[o];
                    if (i.relative[l = f.type]) {
                        break
                    }
                    if (c = i.find[l]) {
                        if (r = c(f.matches[0].replace($, ""), z.test(u[0].type) && t.parentNode || t, s)) {
                            u.splice(o, 1), e = r.length && u.join("");
                            if (!e) {
                                return S.apply(n, x.call(r, 0)), n
                            }
                            break
                        }
                    }
                }
            }
            return a(e, h)(r, t, s, n, z.test(e)), n
        }

        function mt() {
        }

        var n, r, i, s, o, u, a, f, l, c, h = !0, p = "undefined", d = ("sizcache" + Math.random()).replace(".", ""),
            m = String, g = e.document, y = g.documentElement, b = 0, w = 0, E = [].pop, S = [].push, x = [].slice,
            T = [].indexOf || function (e) {
                var t = 0, n = this.length;
                for (; t < n; t++) {
                    if (this[t] === e) {
                        return t
                    }
                }
                return -1
            }, N = function (e, t) {
                return e[d] = t == null || t, e
            }, C = function () {
                var e = {}, t = [];
                return N(function (n, r) {
                    return t.push(n) > i.cacheLength && delete e[t.shift()], e[n + " "] = r
                }, e)
            }, k = C(), L = C(), A = C(), O = "[\\x20\\t\\r\\n\\f]", M = "(?:\\\\.|[-\\w]|[^\\x00-\\xa0])+",
            _ = M.replace("w", "w#"), D = "([*^$|!~]?=)",
            P = "\\[" + O + "*(" + M + ")" + O + "*(?:" + D + O + "*(?:(['\"])((?:\\\\.|[^\\\\])*?)\\3|(" + _ + ")|)|)" + O + "*\\]",
            H = ":(" + M + ")(?:\\((?:(['\"])((?:\\\\.|[^\\\\])*?)\\2|([^()[\\]]*|(?:(?:" + P + ")|[^:]|\\\\.)*|.*))\\)|)",
            B = ":(even|odd|eq|gt|lt|nth|first|last)(?:\\(" + O + "*((?:-\\d)?\\d*)" + O + "*\\)|)(?=[^-]|$)",
            j = new RegExp("^" + O + "+|((?:^|[^\\\\])(?:\\\\.)*)" + O + "+$", "g"),
            F = new RegExp("^" + O + "*," + O + "*"), I = new RegExp("^" + O + "*([\\x20\\t\\r\\n\\f>+~])" + O + "*"),
            q = new RegExp(H), R = /^(?:#([\w\-]+)|(\w+)|\.([\w\-]+))$/, U = /^:not/, z = /[\x20\t\r\n\f]*[+~]/,
            W = /:not\($/, X = /h\d/i, V = /input|select|textarea|button/i, $ = /\\(?!\\)/g, J = {
                ID: new RegExp("^#(" + M + ")"),
                CLASS: new RegExp("^\\.(" + M + ")"),
                NAME: new RegExp("^\\[name=['\"]?(" + M + ")['\"]?\\]"),
                TAG: new RegExp("^(" + M.replace("w", "w*") + ")"),
                ATTR: new RegExp("^" + P),
                PSEUDO: new RegExp("^" + H),
                POS: new RegExp(B, "i"),
                CHILD: new RegExp("^:(only|nth|first|last)-child(?:\\(" + O + "*(even|odd|(([+-]|)(\\d*)n|)" + O + "*(?:([+-]|)" + O + "*(\\d+)|))" + O + "*\\)|)", "i"),
                needsContext: new RegExp("^" + O + "*[>+~]|" + B, "i")
            }, K = function (e) {
                var t = g.createElement("div");
                try {
                    return e(t)
                } catch (n) {
                    return !1
                } finally {
                    t = null
                }
            }, Q = K(function (e) {
                return e.appendChild(g.createComment("")), !e.getElementsByTagName("*").length
            }), G = K(function (e) {
                return e.innerHTML = "<a href='#'></a>", e.firstChild && typeof e.firstChild.getAttribute !== p && e.firstChild.getAttribute("href") === "#"
            }), Y = K(function (e) {
                e.innerHTML = "<select></select>";
                var t = typeof e.lastChild.getAttribute("multiple");
                return t !== "boolean" && t !== "string"
            }), Z = K(function (e) {
                return e.innerHTML = "<div class='hidden e'></div><div class='hidden'></div>", !e.getElementsByClassName || !e.getElementsByClassName("e").length ? !1 : (e.lastChild.className = "e", e.getElementsByClassName("e").length === 2)
            }), et = K(function (e) {
                e.id = d + 0, e.innerHTML = "<a name='" + d + "'></a><div name='" + d + "'></div>", y.insertBefore(e, y.firstChild);
                var t = g.getElementsByName && g.getElementsByName(d).length === 2 + g.getElementsByName(d + 0).length;
                return r = !g.getElementById(d), y.removeChild(e), t
            });
        try {
            x.call(y.childNodes, 0)[0].nodeType
        } catch (tt) {
            x = function (e) {
                var t, n = [];
                for (; t = this[e]; e++) {
                    n.push(t)
                }
                return n
            }
        }
        nt.matches = function (e, t) {
            return nt(e, null, null, t)
        }, nt.matchesSelector = function (e, t) {
            return nt(t, null, null, [e]).length > 0
        }, s = nt.getText = function (e) {
            var t, n = "", r = 0, i = e.nodeType;
            if (i) {
                if (i === 1 || i === 9 || i === 11) {
                    if (typeof e.textContent == "string") {
                        return e.textContent
                    }
                    for (e = e.firstChild; e; e = e.nextSibling) {
                        n += s(e)
                    }
                } else {
                    if (i === 3 || i === 4) {
                        return e.nodeValue
                    }
                }
            } else {
                for (; t = e[r]; r++) {
                    n += s(t)
                }
            }
            return n
        }, o = nt.isXML = function (e) {
            var t = e && (e.ownerDocument || e).documentElement;
            return t ? t.nodeName !== "HTML" : !1
        }, u = nt.contains = y.contains ? function (e, t) {
            var n = e.nodeType === 9 ? e.documentElement : e, r = t && t.parentNode;
            return e === r || !!(r && r.nodeType === 1 && n.contains && n.contains(r))
        } : y.compareDocumentPosition ? function (e, t) {
            return t && !!(e.compareDocumentPosition(t) & 16)
        } : function (e, t) {
            while (t = t.parentNode) {
                if (t === e) {
                    return !0
                }
            }
            return !1
        }, nt.attr = function (e, t) {
            var n, r = o(e);
            return r || (t = t.toLowerCase()), (n = i.attrHandle[t]) ? n(e) : r || Y ? e.getAttribute(t) : (n = e.getAttributeNode(t), n ? typeof e[t] == "boolean" ? e[t] ? t : null : n.specified ? n.value : null : null)
        }, i = nt.selectors = {
            cacheLength: 50,
            createPseudo: N,
            match: J,
            attrHandle: G ? {} : {
                href: function (e) {
                    return e.getAttribute("href", 2)
                }, type: function (e) {
                    return e.getAttribute("type")
                }
            },
            find: {
                ID: r ? function (e, t, n) {
                    if (typeof t.getElementById !== p && !n) {
                        var r = t.getElementById(e);
                        return r && r.parentNode ? [r] : []
                    }
                } : function (e, n, r) {
                    if (typeof n.getElementById !== p && !r) {
                        var i = n.getElementById(e);
                        return i ? i.id === e || typeof i.getAttributeNode !== p && i.getAttributeNode("id").value === e ? [i] : t : []
                    }
                }, TAG: Q ? function (e, t) {
                    if (typeof t.getElementsByTagName !== p) {
                        return t.getElementsByTagName(e)
                    }
                } : function (e, t) {
                    var n = t.getElementsByTagName(e);
                    if (e === "*") {
                        var r, i = [], s = 0;
                        for (; r = n[s]; s++) {
                            r.nodeType === 1 && i.push(r)
                        }
                        return i
                    }
                    return n
                }, NAME: et && function (e, t) {
                    if (typeof t.getElementsByName !== p) {
                        return t.getElementsByName(name)
                    }
                }, CLASS: Z && function (e, t, n) {
                    if (typeof t.getElementsByClassName !== p && !n) {
                        return t.getElementsByClassName(e)
                    }
                }
            },
            relative: {
                ">": {dir: "parentNode", first: !0},
                " ": {dir: "parentNode"},
                "+": {dir: "previousSibling", first: !0},
                "~": {dir: "previousSibling"}
            },
            preFilter: {
                ATTR: function (e) {
                    return e[1] = e[1].replace($, ""), e[3] = (e[4] || e[5] || "").replace($, ""), e[2] === "~=" && (e[3] = " " + e[3] + " "), e.slice(0, 4)
                }, CHILD: function (e) {
                    return e[1] = e[1].toLowerCase(), e[1] === "nth" ? (e[2] || nt.error(e[0]), e[3] = +(e[3] ? e[4] + (e[5] || 1) : 2 * (e[2] === "even" || e[2] === "odd")), e[4] = +(e[6] + e[7] || e[2] === "odd")) : e[2] && nt.error(e[0]), e
                }, PSEUDO: function (e) {
                    var t, n;
                    if (J.CHILD.test(e[0])) {
                        return null
                    }
                    if (e[3]) {
                        e[2] = e[3]
                    } else {
                        if (t = e[4]) {
                            q.test(t) && (n = ut(t, !0)) && (n = t.indexOf(")", t.length - n) - t.length) && (t = t.slice(0, n), e[0] = e[0].slice(0, n)), e[2] = t
                        }
                    }
                    return e.slice(0, 3)
                }
            },
            filter: {
                ID: r ? function (e) {
                    return e = e.replace($, ""), function (t) {
                        return t.getAttribute("id") === e
                    }
                } : function (e) {
                    return e = e.replace($, ""), function (t) {
                        var n = typeof t.getAttributeNode !== p && t.getAttributeNode("id");
                        return n && n.value === e
                    }
                }, TAG: function (e) {
                    return e === "*" ? function () {
                        return !0
                    } : (e = e.replace($, "").toLowerCase(), function (t) {
                        return t.nodeName && t.nodeName.toLowerCase() === e
                    })
                }, CLASS: function (e) {
                    var t = k[d][e + " "];
                    return t || (t = new RegExp("(^|" + O + ")" + e + "(" + O + "|$)")) && k(e, function (e) {
                        return t.test(e.className || typeof e.getAttribute !== p && e.getAttribute("class") || "")
                    })
                }, ATTR: function (e, t, n) {
                    return function (r, i) {
                        var s = nt.attr(r, e);
                        return s == null ? t === "!=" : t ? (s += "", t === "=" ? s === n : t === "!=" ? s !== n : t === "^=" ? n && s.indexOf(n) === 0 : t === "*=" ? n && s.indexOf(n) > -1 : t === "$=" ? n && s.substr(s.length - n.length) === n : t === "~=" ? (" " + s + " ").indexOf(n) > -1 : t === "|=" ? s === n || s.substr(0, n.length + 1) === n + "-" : !1) : !0
                    }
                }, CHILD: function (e, t, n, r) {
                    return e === "nth" ? function (e) {
                        var t, i, s = e.parentNode;
                        if (n === 1 && r === 0) {
                            return !0
                        }
                        if (s) {
                            i = 0;
                            for (t = s.firstChild; t; t = t.nextSibling) {
                                if (t.nodeType === 1) {
                                    i++;
                                    if (e === t) {
                                        break
                                    }
                                }
                            }
                        }
                        return i -= r, i === n || i % n === 0 && i / n >= 0
                    } : function (t) {
                        var n = t;
                        switch (e) {
                            case"only":
                            case"first":
                                while (n = n.previousSibling) {
                                    if (n.nodeType === 1) {
                                        return !1
                                    }
                                }
                                if (e === "first") {
                                    return !0
                                }
                                n = t;
                            case"last":
                                while (n = n.nextSibling) {
                                    if (n.nodeType === 1) {
                                        return !1
                                    }
                                }
                                return !0
                        }
                    }
                }, PSEUDO: function (e, t) {
                    var n, r = i.pseudos[e] || i.setFilters[e.toLowerCase()] || nt.error("unsupported pseudo: " + e);
                    return r[d] ? r(t) : r.length > 1 ? (n = [e, e, "", t], i.setFilters.hasOwnProperty(e.toLowerCase()) ? N(function (e, n) {
                        var i, s = r(e, t), o = s.length;
                        while (o--) {
                            i = T.call(e, s[o]), e[i] = !(n[i] = s[o])
                        }
                    }) : function (e) {
                        return r(e, 0, n)
                    }) : r
                }
            },
            pseudos: {
                not: N(function (e) {
                    var t = [], n = [], r = a(e.replace(j, "$1"));
                    return r[d] ? N(function (e, t, n, i) {
                        var s, o = r(e, null, i, []), u = e.length;
                        while (u--) {
                            if (s = o[u]) {
                                e[u] = !(t[u] = s)
                            }
                        }
                    }) : function (e, i, s) {
                        return t[0] = e, r(t, null, s, n), !n.pop()
                    }
                }),
                has: N(function (e) {
                    return function (t) {
                        return nt(e, t).length > 0
                    }
                }),
                contains: N(function (e) {
                    return function (t) {
                        return (t.textContent || t.innerText || s(t)).indexOf(e) > -1
                    }
                }),
                enabled: function (e) {
                    return e.disabled === !1
                },
                disabled: function (e) {
                    return e.disabled === !0
                },
                checked: function (e) {
                    var t = e.nodeName.toLowerCase();
                    return t === "input" && !!e.checked || t === "option" && !!e.selected
                },
                selected: function (e) {
                    return e.parentNode && e.parentNode.selectedIndex, e.selected === !0
                },
                parent: function (e) {
                    return !i.pseudos.empty(e)
                },
                empty: function (e) {
                    var t;
                    e = e.firstChild;
                    while (e) {
                        if (e.nodeName > "@" || (t = e.nodeType) === 3 || t === 4) {
                            return !1
                        }
                        e = e.nextSibling
                    }
                    return !0
                },
                header: function (e) {
                    return X.test(e.nodeName)
                },
                text: function (e) {
                    var t, n;
                    return e.nodeName.toLowerCase() === "input" && (t = e.type) === "text" && ((n = e.getAttribute("type")) == null || n.toLowerCase() === t)
                },
                radio: rt("radio"),
                checkbox: rt("checkbox"),
                file: rt("file"),
                password: rt("password"),
                image: rt("image"),
                submit: it("submit"),
                reset: it("reset"),
                button: function (e) {
                    var t = e.nodeName.toLowerCase();
                    return t === "input" && e.type === "button" || t === "button"
                },
                input: function (e) {
                    return V.test(e.nodeName)
                },
                focus: function (e) {
                    var t = e.ownerDocument;
                    return e === t.activeElement && (!t.hasFocus || t.hasFocus()) && !!(e.type || e.href || ~e.tabIndex)
                },
                active: function (e) {
                    return e === e.ownerDocument.activeElement
                },
                first: st(function () {
                    return [0]
                }),
                last: st(function (e, t) {
                    return [t - 1]
                }),
                eq: st(function (e, t, n) {
                    return [n < 0 ? n + t : n]
                }),
                even: st(function (e, t) {
                    for (var n = 0; n < t; n += 2) {
                        e.push(n)
                    }
                    return e
                }),
                odd: st(function (e, t) {
                    for (var n = 1; n < t; n += 2) {
                        e.push(n)
                    }
                    return e
                }),
                lt: st(function (e, t, n) {
                    for (var r = n < 0 ? n + t : n; --r >= 0;) {
                        e.push(r)
                    }
                    return e
                }),
                gt: st(function (e, t, n) {
                    for (var r = n < 0 ? n + t : n; ++r < t;) {
                        e.push(r)
                    }
                    return e
                })
            }
        }, f = y.compareDocumentPosition ? function (e, t) {
            return e === t ? (l = !0, 0) : (!e.compareDocumentPosition || !t.compareDocumentPosition ? e.compareDocumentPosition : e.compareDocumentPosition(t) & 4) ? -1 : 1
        } : function (e, t) {
            if (e === t) {
                return l = !0, 0
            }
            if (e.sourceIndex && t.sourceIndex) {
                return e.sourceIndex - t.sourceIndex
            }
            var n, r, i = [], s = [], o = e.parentNode, u = t.parentNode, a = o;
            if (o === u) {
                return ot(e, t)
            }
            if (!o) {
                return -1
            }
            if (!u) {
                return 1
            }
            while (a) {
                i.unshift(a), a = a.parentNode
            }
            a = u;
            while (a) {
                s.unshift(a), a = a.parentNode
            }
            n = i.length, r = s.length;
            for (var f = 0; f < n && f < r; f++) {
                if (i[f] !== s[f]) {
                    return ot(i[f], s[f])
                }
            }
            return f === n ? ot(e, s[f], -1) : ot(i[f], t, 1)
        }, [0, 0].sort(f), h = !l, nt.uniqueSort = function (e) {
            var t, n = [], r = 1, i = 0;
            l = h, e.sort(f);
            if (l) {
                for (; t = e[r]; r++) {
                    t === e[r - 1] && (i = n.push(r))
                }
                while (i--) {
                    e.splice(n[i], 1)
                }
            }
            return e
        }, nt.error = function (e) {
            throw new Error("Syntax error, unrecognized expression: " + e)
        }, a = nt.compile = function (e, t) {
            var n, r = [], i = [], s = A[d][e + " "];
            if (!s) {
                t || (t = ut(e)), n = t.length;
                while (n--) {
                    s = ht(t[n]), s[d] ? r.push(s) : i.push(s)
                }
                s = A(e, pt(i, r))
            }
            return s
        }, g.querySelectorAll && function () {
            var e, t = vt, n = /'|\\/g, r = /\=[\x20\t\r\n\f]*([^'"\]]*)[\x20\t\r\n\f]*\]/g, i = [":focus"],
                s = [":active"],
                u = y.matchesSelector || y.mozMatchesSelector || y.webkitMatchesSelector || y.oMatchesSelector || y.msMatchesSelector;
            K(function (e) {
                e.innerHTML = "<select><option selected=''></option></select>", e.querySelectorAll("[selected]").length || i.push("\\[" + O + "*(?:checked|disabled|ismap|multiple|readonly|selected|value)"), e.querySelectorAll(":checked").length || i.push(":checked")
            }), K(function (e) {
                e.innerHTML = "<p test=''></p>", e.querySelectorAll("[test^='']").length && i.push("[*^$]=" + O + "*(?:\"\"|'')"), e.innerHTML = "<input type='hidden'/>", e.querySelectorAll(":enabled").length || i.push(":enabled", ":disabled")
            }), i = new RegExp(i.join("|")), vt = function (e, r, s, o, u) {
                if (!o && !u && !i.test(e)) {
                    var a, f, l = !0, c = d, h = r, p = r.nodeType === 9 && e;
                    if (r.nodeType === 1 && r.nodeName.toLowerCase() !== "object") {
                        a = ut(e), (l = r.getAttribute("id")) ? c = l.replace(n, "\\$&") : r.setAttribute("id", c), c = "[id='" + c + "'] ", f = a.length;
                        while (f--) {
                            a[f] = c + a[f].join("")
                        }
                        h = z.test(e) && r.parentNode || r, p = a.join(",")
                    }
                    if (p) {
                        try {
                            return S.apply(s, x.call(h.querySelectorAll(p), 0)), s
                        } catch (v) {
                        } finally {
                            l || r.removeAttribute("id")
                        }
                    }
                }
                return t(e, r, s, o, u)
            }, u && (K(function (t) {
                e = u.call(t, "div");
                try {
                    u.call(t, "[test!='']:sizzle"), s.push("!=", H)
                } catch (n) {
                }
            }), s = new RegExp(s.join("|")), nt.matchesSelector = function (t, n) {
                n = n.replace(r, "='$1']");
                if (!o(t) && !s.test(n) && !i.test(n)) {
                    try {
                        var a = u.call(t, n);
                        if (a || e || t.document && t.document.nodeType !== 11) {
                            return a
                        }
                    } catch (f) {
                    }
                }
                return nt(n, null, null, [t]).length > 0
            })
        }(), i.pseudos.nth = i.pseudos.eq, i.filters = mt.prototype = i.pseudos, i.setFilters = new mt, nt.attr = v.attr, v.find = nt, v.expr = nt.selectors, v.expr[":"] = v.expr.pseudos, v.unique = nt.uniqueSort, v.text = nt.getText, v.isXMLDoc = nt.isXML, v.contains = nt.contains
    }(e);
    var nt = /Until$/, rt = /^(?:parents|prev(?:Until|All))/, it = /^.[^:#\[\.,]*$/, st = v.expr.match.needsContext,
        ot = {children: !0, contents: !0, next: !0, prev: !0};
    v.fn.extend({
        find: function (e) {
            var t, n, r, i, s, o, u = this;
            if (typeof e != "string") {
                return v(e).filter(function () {
                    for (t = 0, n = u.length; t < n; t++) {
                        if (v.contains(u[t], this)) {
                            return !0
                        }
                    }
                })
            }
            o = this.pushStack("", "find", e);
            for (t = 0, n = this.length; t < n; t++) {
                r = o.length, v.find(e, this[t], o);
                if (t > 0) {
                    for (i = r; i < o.length; i++) {
                        for (s = 0; s < r; s++) {
                            if (o[s] === o[i]) {
                                o.splice(i--, 1);
                                break
                            }
                        }
                    }
                }
            }
            return o
        }, has: function (e) {
            var t, n = v(e, this), r = n.length;
            return this.filter(function () {
                for (t = 0; t < r; t++) {
                    if (v.contains(this, n[t])) {
                        return !0
                    }
                }
            })
        }, not: function (e) {
            return this.pushStack(ft(this, e, !1), "not", e)
        }, filter: function (e) {
            return this.pushStack(ft(this, e, !0), "filter", e)
        }, is: function (e) {
            return !!e && (typeof e == "string" ? st.test(e) ? v(e, this.context).index(this[0]) >= 0 : v.filter(e, this).length > 0 : this.filter(e).length > 0)
        }, closest: function (e, t) {
            var n, r = 0, i = this.length, s = [], o = st.test(e) || typeof e != "string" ? v(e, t || this.context) : 0;
            for (; r < i; r++) {
                n = this[r];
                while (n && n.ownerDocument && n !== t && n.nodeType !== 11) {
                    if (o ? o.index(n) > -1 : v.find.matchesSelector(n, e)) {
                        s.push(n);
                        break
                    }
                    n = n.parentNode
                }
            }
            return s = s.length > 1 ? v.unique(s) : s, this.pushStack(s, "closest", e)
        }, index: function (e) {
            return e ? typeof e == "string" ? v.inArray(this[0], v(e)) : v.inArray(e.jquery ? e[0] : e, this) : this[0] && this[0].parentNode ? this.prevAll().length : -1
        }, add: function (e, t) {
            var n = typeof e == "string" ? v(e, t) : v.makeArray(e && e.nodeType ? [e] : e), r = v.merge(this.get(), n);
            return this.pushStack(ut(n[0]) || ut(r[0]) ? r : v.unique(r))
        }, addBack: function (e) {
            return this.add(e == null ? this.prevObject : this.prevObject.filter(e))
        }
    }), v.fn.andSelf = v.fn.addBack, v.each({
        parent: function (e) {
            var t = e.parentNode;
            return t && t.nodeType !== 11 ? t : null
        }, parents: function (e) {
            return v.dir(e, "parentNode")
        }, parentsUntil: function (e, t, n) {
            return v.dir(e, "parentNode", n)
        }, next: function (e) {
            return at(e, "nextSibling")
        }, prev: function (e) {
            return at(e, "previousSibling")
        }, nextAll: function (e) {
            return v.dir(e, "nextSibling")
        }, prevAll: function (e) {
            return v.dir(e, "previousSibling")
        }, nextUntil: function (e, t, n) {
            return v.dir(e, "nextSibling", n)
        }, prevUntil: function (e, t, n) {
            return v.dir(e, "previousSibling", n)
        }, siblings: function (e) {
            return v.sibling((e.parentNode || {}).firstChild, e)
        }, children: function (e) {
            return v.sibling(e.firstChild)
        }, contents: function (e) {
            return v.nodeName(e, "iframe") ? e.contentDocument || e.contentWindow.document : v.merge([], e.childNodes)
        }
    }, function (e, t) {
        v.fn[e] = function (n, r) {
            var i = v.map(this, t, n);
            return nt.test(e) || (r = n), r && typeof r == "string" && (i = v.filter(r, i)), i = this.length > 1 && !ot[e] ? v.unique(i) : i, this.length > 1 && rt.test(e) && (i = i.reverse()), this.pushStack(i, e, l.call(arguments).join(","))
        }
    }), v.extend({
        filter: function (e, t, n) {
            return n && (e = ":not(" + e + ")"), t.length === 1 ? v.find.matchesSelector(t[0], e) ? [t[0]] : [] : v.find.matches(e, t)
        }, dir: function (e, n, r) {
            var i = [], s = e[n];
            while (s && s.nodeType !== 9 && (r === t || s.nodeType !== 1 || !v(s).is(r))) {
                s.nodeType === 1 && i.push(s), s = s[n]
            }
            return i
        }, sibling: function (e, t) {
            var n = [];
            for (; e; e = e.nextSibling) {
                e.nodeType === 1 && e !== t && n.push(e)
            }
            return n
        }
    });
    var ct = "abbr|article|aside|audio|bdi|canvas|data|datalist|details|figcaption|figure|footer|header|hgroup|mark|meter|nav|output|progress|section|summary|time|video",
        ht = / jQuery\d+="(?:null|\d+)"/g, pt = /^\s+/,
        dt = /<(?!area|br|col|embed|hr|img|input|link|meta|param)(([\w:]+)[^>]*)\/>/gi, vt = /<([\w:]+)/,
        mt = /<tbody/i, gt = /<|&#?\w+;/, yt = /<(?:script|style|link)/i, bt = /<(?:script|object|embed|option|style)/i,
        wt = new RegExp("<(?:" + ct + ")[\\s/>]", "i"), Et = /^(?:checkbox|radio)$/,
        St = /checked\s*(?:[^=]|=\s*.checked.)/i, xt = /\/(java|ecma)script/i,
        Tt = /^\s*<!(?:\[CDATA\[|\-\-)|[\]\-]{2}>\s*$/g, Nt = {
            option: [1, "<select multiple='multiple'>", "</select>"],
            legend: [1, "<fieldset>", "</fieldset>"],
            thead: [1, "<table>", "</table>"],
            tr: [2, "<table><tbody>", "</tbody></table>"],
            td: [3, "<table><tbody><tr>", "</tr></tbody></table>"],
            col: [2, "<table><tbody></tbody><colgroup>", "</colgroup></table>"],
            area: [1, "<map>", "</map>"],
            _default: [0, "", ""]
        }, Ct = lt(i), kt = Ct.appendChild(i.createElement("div"));
    Nt.optgroup = Nt.option, Nt.tbody = Nt.tfoot = Nt.colgroup = Nt.caption = Nt.thead, Nt.th = Nt.td, v.support.htmlSerialize || (Nt._default = [1, "X<div>", "</div>"]), v.fn.extend({
        text: function (e) {
            return v.access(this, function (e) {
                return e === t ? v.text(this) : this.empty().append((this[0] && this[0].ownerDocument || i).createTextNode(e))
            }, null, e, arguments.length)
        }, wrapAll: function (e) {
            if (v.isFunction(e)) {
                return this.each(function (t) {
                    v(this).wrapAll(e.call(this, t))
                })
            }
            if (this[0]) {
                var t = v(e, this[0].ownerDocument).eq(0).clone(!0);
                this[0].parentNode && t.insertBefore(this[0]), t.map(function () {
                    var e = this;
                    while (e.firstChild && e.firstChild.nodeType === 1) {
                        e = e.firstChild
                    }
                    return e
                }).append(this)
            }
            return this
        }, wrapInner: function (e) {
            return v.isFunction(e) ? this.each(function (t) {
                v(this).wrapInner(e.call(this, t))
            }) : this.each(function () {
                var t = v(this), n = t.contents();
                n.length ? n.wrapAll(e) : t.append(e)
            })
        }, wrap: function (e) {
            var t = v.isFunction(e);
            return this.each(function (n) {
                v(this).wrapAll(t ? e.call(this, n) : e)
            })
        }, unwrap: function () {
            return this.parent().each(function () {
                v.nodeName(this, "body") || v(this).replaceWith(this.childNodes)
            }).end()
        }, append: function () {
            return this.domManip(arguments, !0, function (e) {
                (this.nodeType === 1 || this.nodeType === 11) && this.appendChild(e)
            })
        }, prepend: function () {
            return this.domManip(arguments, !0, function (e) {
                (this.nodeType === 1 || this.nodeType === 11) && this.insertBefore(e, this.firstChild)
            })
        }, before: function () {
            if (!ut(this[0])) {
                return this.domManip(arguments, !1, function (e) {
                    this.parentNode.insertBefore(e, this)
                })
            }
            if (arguments.length) {
                var e = v.clean(arguments);
                return this.pushStack(v.merge(e, this), "before", this.selector)
            }
        }, after: function () {
            if (!ut(this[0])) {
                return this.domManip(arguments, !1, function (e) {
                    this.parentNode.insertBefore(e, this.nextSibling)
                })
            }
            if (arguments.length) {
                var e = v.clean(arguments);
                return this.pushStack(v.merge(this, e), "after", this.selector)
            }
        }, remove: function (e, t) {
            var n, r = 0;
            for (; (n = this[r]) != null; r++) {
                if (!e || v.filter(e, [n]).length) {
                    !t && n.nodeType === 1 && (v.cleanData(n.getElementsByTagName("*")), v.cleanData([n])), n.parentNode && n.parentNode.removeChild(n)
                }
            }
            return this
        }, empty: function () {
            var e, t = 0;
            for (; (e = this[t]) != null; t++) {
                e.nodeType === 1 && v.cleanData(e.getElementsByTagName("*"));
                while (e.firstChild) {
                    e.removeChild(e.firstChild)
                }
            }
            return this
        }, clone: function (e, t) {
            return e = e == null ? !1 : e, t = t == null ? e : t, this.map(function () {
                return v.clone(this, e, t)
            })
        }, html: function (e) {
            return v.access(this, function (e) {
                var n = this[0] || {}, r = 0, i = this.length;
                if (e === t) {
                    return n.nodeType === 1 ? n.innerHTML.replace(ht, "") : t
                }
                if (typeof e == "string" && !yt.test(e) && (v.support.htmlSerialize || !wt.test(e)) && (v.support.leadingWhitespace || !pt.test(e)) && !Nt[(vt.exec(e) || ["", ""])[1].toLowerCase()]) {
                    e = e.replace(dt, "<$1></$2>");
                    try {
                        for (; r < i; r++) {
                            n = this[r] || {}, n.nodeType === 1 && (v.cleanData(n.getElementsByTagName("*")), n.innerHTML = e)
                        }
                        n = 0
                    } catch (s) {
                    }
                }
                n && this.empty().append(e)
            }, null, e, arguments.length)
        }, replaceWith: function (e) {
            return ut(this[0]) ? this.length ? this.pushStack(v(v.isFunction(e) ? e() : e), "replaceWith", e) : this : v.isFunction(e) ? this.each(function (t) {
                var n = v(this), r = n.html();
                n.replaceWith(e.call(this, t, r))
            }) : (typeof e != "string" && (e = v(e).detach()), this.each(function () {
                var t = this.nextSibling, n = this.parentNode;
                v(this).remove(), t ? v(t).before(e) : v(n).append(e)
            }))
        }, detach: function (e) {
            return this.remove(e, !0)
        }, domManip: function (e, n, r) {
            e = [].concat.apply([], e);
            var i, s, o, u, a = 0, f = e[0], l = [], c = this.length;
            if (!v.support.checkClone && c > 1 && typeof f == "string" && St.test(f)) {
                return this.each(function () {
                    v(this).domManip(e, n, r)
                })
            }
            if (v.isFunction(f)) {
                return this.each(function (i) {
                    var s = v(this);
                    e[0] = f.call(this, i, n ? s.html() : t), s.domManip(e, n, r)
                })
            }
            if (this[0]) {
                i = v.buildFragment(e, this, l), o = i.fragment, s = o.firstChild, o.childNodes.length === 1 && (o = s);
                if (s) {
                    n = n && v.nodeName(s, "tr");
                    for (u = i.cacheable || c - 1; a < c; a++) {
                        r.call(n && v.nodeName(this[a], "table") ? Lt(this[a], "tbody") : this[a], a === u ? o : v.clone(o, !0, !0))
                    }
                }
                o = s = null, l.length && v.each(l, function (e, t) {
                    t.src ? v.ajax ? v.ajax({
                        url: t.src,
                        type: "GET",
                        dataType: "script",
                        async: !1,
                        global: !1,
                        "throws": !0
                    }) : v.error("no ajax") : v.globalEval((t.text || t.textContent || t.innerHTML || "").replace(Tt, "")), t.parentNode && t.parentNode.removeChild(t)
                })
            }
            return this
        }
    }), v.buildFragment = function (e, n, r) {
        var s, o, u, a = e[0];
        return n = n || i, n = !n.nodeType && n[0] || n, n = n.ownerDocument || n, e.length === 1 && typeof a == "string" && a.length < 512 && n === i && a.charAt(0) === "<" && !bt.test(a) && (v.support.checkClone || !St.test(a)) && (v.support.html5Clone || !wt.test(a)) && (o = !0, s = v.fragments[a], u = s !== t), s || (s = n.createDocumentFragment(), v.clean(e, n, s, r), o && (v.fragments[a] = u && s)), {
            fragment: s,
            cacheable: o
        }
    }, v.fragments = {}, v.each({
        appendTo: "append",
        prependTo: "prepend",
        insertBefore: "before",
        insertAfter: "after",
        replaceAll: "replaceWith"
    }, function (e, t) {
        v.fn[e] = function (n) {
            var r, i = 0, s = [], o = v(n), u = o.length, a = this.length === 1 && this[0].parentNode;
            if ((a == null || a && a.nodeType === 11 && a.childNodes.length === 1) && u === 1) {
                return o[t](this[0]), this
            }
            for (; i < u; i++) {
                r = (i > 0 ? this.clone(!0) : this).get(), v(o[i])[t](r), s = s.concat(r)
            }
            return this.pushStack(s, e, o.selector)
        }
    }), v.extend({
        clone: function (e, t, n) {
            var r, i, s, o;
            v.support.html5Clone || v.isXMLDoc(e) || !wt.test("<" + e.nodeName + ">") ? o = e.cloneNode(!0) : (kt.innerHTML = e.outerHTML, kt.removeChild(o = kt.firstChild));
            if ((!v.support.noCloneEvent || !v.support.noCloneChecked) && (e.nodeType === 1 || e.nodeType === 11) && !v.isXMLDoc(e)) {
                Ot(e, o), r = Mt(e), i = Mt(o);
                for (s = 0; r[s]; ++s) {
                    i[s] && Ot(r[s], i[s])
                }
            }
            if (t) {
                At(e, o);
                if (n) {
                    r = Mt(e), i = Mt(o);
                    for (s = 0; r[s]; ++s) {
                        At(r[s], i[s])
                    }
                }
            }
            return r = i = null, o
        }, clean: function (e, t, n, r) {
            var s, o, u, a, f, l, c, h, p, d, m, g, y = t === i && Ct, b = [];
            if (!t || typeof t.createDocumentFragment == "undefined") {
                t = i
            }
            for (s = 0; (u = e[s]) != null; s++) {
                typeof u == "number" && (u += "");
                if (!u) {
                    continue
                }
                if (typeof u == "string") {
                    if (!gt.test(u)) {
                        u = t.createTextNode(u)
                    } else {
                        y = y || lt(t), c = t.createElement("div"), y.appendChild(c), u = u.replace(dt, "<$1></$2>"), a = (vt.exec(u) || ["", ""])[1].toLowerCase(), f = Nt[a] || Nt._default, l = f[0], c.innerHTML = f[1] + u + f[2];
                        while (l--) {
                            c = c.lastChild
                        }
                        if (!v.support.tbody) {
                            h = mt.test(u), p = a === "table" && !h ? c.firstChild && c.firstChild.childNodes : f[1] === "<table>" && !h ? c.childNodes : [];
                            for (o = p.length - 1; o >= 0; --o) {
                                v.nodeName(p[o], "tbody") && !p[o].childNodes.length && p[o].parentNode.removeChild(p[o])
                            }
                        }
                        !v.support.leadingWhitespace && pt.test(u) && c.insertBefore(t.createTextNode(pt.exec(u)[0]), c.firstChild), u = c.childNodes, c.parentNode.removeChild(c)
                    }
                }
                u.nodeType ? b.push(u) : v.merge(b, u)
            }
            c && (u = c = y = null);
            if (!v.support.appendChecked) {
                for (s = 0; (u = b[s]) != null; s++) {
                    v.nodeName(u, "input") ? _t(u) : typeof u.getElementsByTagName != "undefined" && v.grep(u.getElementsByTagName("input"), _t)
                }
            }
            if (n) {
                m = function (e) {
                    if (!e.type || xt.test(e.type)) {
                        return r ? r.push(e.parentNode ? e.parentNode.removeChild(e) : e) : n.appendChild(e)
                    }
                };
                for (s = 0; (u = b[s]) != null; s++) {
                    if (!v.nodeName(u, "script") || !m(u)) {
                        n.appendChild(u), typeof u.getElementsByTagName != "undefined" && (g = v.grep(v.merge([], u.getElementsByTagName("script")), m), b.splice.apply(b, [s + 1, 0].concat(g)), s += g.length)
                    }
                }
            }
            return b
        }, cleanData: function (e, t) {
            var n, r, i, s, o = 0, u = v.expando, a = v.cache, f = v.support.deleteExpando, l = v.event.special;
            for (; (i = e[o]) != null; o++) {
                if (t || v.acceptData(i)) {
                    r = i[u], n = r && a[r];
                    if (n) {
                        if (n.events) {
                            for (s in n.events) {
                                l[s] ? v.event.remove(i, s) : v.removeEvent(i, s, n.handle)
                            }
                        }
                        a[r] && (delete a[r], f ? delete i[u] : i.removeAttribute ? i.removeAttribute(u) : i[u] = null, v.deletedIds.push(r))
                    }
                }
            }
        }
    }), function () {
        var e, t;
        v.uaMatch = function (e) {
            e = e.toLowerCase();
            var t = /(chrome)[ \/]([\w.]+)/.exec(e) || /(webkit)[ \/]([\w.]+)/.exec(e) || /(opera)(?:.*version|)[ \/]([\w.]+)/.exec(e) || /(msie) ([\w.]+)/.exec(e) || e.indexOf("compatible") < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec(e) || [];
            return {browser: t[1] || "", version: t[2] || "0"}
        }, e = v.uaMatch(o.userAgent), t = {}, e.browser && (t[e.browser] = !0, t.version = e.version), t.chrome ? t.webkit = !0 : t.webkit && (t.safari = !0), v.browser = t, v.sub = function () {
            function e(t, n) {
                return new e.fn.init(t, n)
            }

            v.extend(!0, e, this), e.superclass = this, e.fn = e.prototype = this(), e.fn.constructor = e, e.sub = this.sub, e.fn.init = function (r, i) {
                return i && i instanceof v && !(i instanceof e) && (i = e(i)), v.fn.init.call(this, r, i, t)
            }, e.fn.init.prototype = e.fn;
            var t = e(i);
            return e
        }
    }();
    var Dt, Pt, Ht, Bt = /alpha\([^)]*\)/i, jt = /opacity=([^)]*)/, Ft = /^(top|right|bottom|left)$/,
        It = /^(none|table(?!-c[ea]).+)/, qt = /^margin/, Rt = new RegExp("^(" + m + ")(.*)$", "i"),
        Ut = new RegExp("^(" + m + ")(?!px)[a-z%]+$", "i"), zt = new RegExp("^([-+])=(" + m + ")", "i"),
        Wt = {BODY: "block"}, Xt = {position: "absolute", visibility: "hidden", display: "block"},
        Vt = {letterSpacing: 0, fontWeight: 400}, $t = ["Top", "Right", "Bottom", "Left"],
        Jt = ["Webkit", "O", "Moz", "ms"], Kt = v.fn.toggle;
    v.fn.extend({
        css: function (e, n) {
            return v.access(this, function (e, n, r) {
                return r !== t ? v.style(e, n, r) : v.css(e, n)
            }, e, n, arguments.length > 1)
        }, show: function () {
            return Yt(this, !0)
        }, hide: function () {
            return Yt(this)
        }, toggle: function (e, t) {
            var n = typeof e == "boolean";
            return v.isFunction(e) && v.isFunction(t) ? Kt.apply(this, arguments) : this.each(function () {
                (n ? e : Gt(this)) ? v(this).show() : v(this).hide()
            })
        }
    }), v.extend({
        cssHooks: {
            opacity: {
                get: function (e, t) {
                    if (t) {
                        var n = Dt(e, "opacity");
                        return n === "" ? "1" : n
                    }
                }
            }
        },
        cssNumber: {
            fillOpacity: !0,
            fontWeight: !0,
            lineHeight: !0,
            opacity: !0,
            orphans: !0,
            widows: !0,
            zIndex: !0,
            zoom: !0
        },
        cssProps: {"float": v.support.cssFloat ? "cssFloat" : "styleFloat"},
        style: function (e, n, r, i) {
            if (!e || e.nodeType === 3 || e.nodeType === 8 || !e.style) {
                return
            }
            var s, o, u, a = v.camelCase(n), f = e.style;
            n = v.cssProps[a] || (v.cssProps[a] = Qt(f, a)), u = v.cssHooks[n] || v.cssHooks[a];
            if (r === t) {
                return u && "get" in u && (s = u.get(e, !1, i)) !== t ? s : f[n]
            }
            o = typeof r, o === "string" && (s = zt.exec(r)) && (r = (s[1] + 1) * s[2] + parseFloat(v.css(e, n)), o = "number");
            if (r == null || o === "number" && isNaN(r)) {
                return
            }
            o === "number" && !v.cssNumber[a] && (r += "px");
            if (!u || !("set" in u) || (r = u.set(e, r, i)) !== t) {
                try {
                    f[n] = r
                } catch (l) {
                }
            }
        },
        css: function (e, n, r, i) {
            var s, o, u, a = v.camelCase(n);
            return n = v.cssProps[a] || (v.cssProps[a] = Qt(e.style, a)), u = v.cssHooks[n] || v.cssHooks[a], u && "get" in u && (s = u.get(e, !0, i)), s === t && (s = Dt(e, n)), s === "normal" && n in Vt && (s = Vt[n]), r || i !== t ? (o = parseFloat(s), r || v.isNumeric(o) ? o || 0 : s) : s
        },
        swap: function (e, t, n) {
            var r, i, s = {};
            for (i in t) {
                s[i] = e.style[i], e.style[i] = t[i]
            }
            r = n.call(e);
            for (i in t) {
                e.style[i] = s[i]
            }
            return r
        }
    }), e.getComputedStyle ? Dt = function (t, n) {
        var r, i, s, o, u = e.getComputedStyle(t, null), a = t.style;
        return u && (r = u.getPropertyValue(n) || u[n], r === "" && !v.contains(t.ownerDocument, t) && (r = v.style(t, n)), Ut.test(r) && qt.test(n) && (i = a.width, s = a.minWidth, o = a.maxWidth, a.minWidth = a.maxWidth = a.width = r, r = u.width, a.width = i, a.minWidth = s, a.maxWidth = o)), r
    } : i.documentElement.currentStyle && (Dt = function (e, t) {
        var n, r, i = e.currentStyle && e.currentStyle[t], s = e.style;
        return i == null && s && s[t] && (i = s[t]), Ut.test(i) && !Ft.test(t) && (n = s.left, r = e.runtimeStyle && e.runtimeStyle.left, r && (e.runtimeStyle.left = e.currentStyle.left), s.left = t === "fontSize" ? "1em" : i, i = s.pixelLeft + "px", s.left = n, r && (e.runtimeStyle.left = r)), i === "" ? "auto" : i
    }), v.each(["height", "width"], function (e, t) {
        v.cssHooks[t] = {
            get: function (e, n, r) {
                if (n) {
                    return e.offsetWidth === 0 && It.test(Dt(e, "display")) ? v.swap(e, Xt, function () {
                        return tn(e, t, r)
                    }) : tn(e, t, r)
                }
            }, set: function (e, n, r) {
                return Zt(e, n, r ? en(e, t, r, v.support.boxSizing && v.css(e, "boxSizing") === "border-box") : 0)
            }
        }
    }), v.support.opacity || (v.cssHooks.opacity = {
        get: function (e, t) {
            return jt.test((t && e.currentStyle ? e.currentStyle.filter : e.style.filter) || "") ? 0.01 * parseFloat(RegExp.$1) + "" : t ? "1" : ""
        }, set: function (e, t) {
            var n = e.style, r = e.currentStyle, i = v.isNumeric(t) ? "alpha(opacity=" + t * 100 + ")" : "",
                s = r && r.filter || n.filter || "";
            n.zoom = 1;
            if (t >= 1 && v.trim(s.replace(Bt, "")) === "" && n.removeAttribute) {
                n.removeAttribute("filter");
                if (r && !r.filter) {
                    return
                }
            }
            n.filter = Bt.test(s) ? s.replace(Bt, i) : s + " " + i
        }
    }), v(function () {
        v.support.reliableMarginRight || (v.cssHooks.marginRight = {
            get: function (e, t) {
                return v.swap(e, {display: "inline-block"}, function () {
                    if (t) {
                        return Dt(e, "marginRight")
                    }
                })
            }
        }), !v.support.pixelPosition && v.fn.position && v.each(["top", "left"], function (e, t) {
            v.cssHooks[t] = {
                get: function (e, n) {
                    if (n) {
                        var r = Dt(e, t);
                        return Ut.test(r) ? v(e).position()[t] + "px" : r
                    }
                }
            }
        })
    }), v.expr && v.expr.filters && (v.expr.filters.hidden = function (e) {
        return e.offsetWidth === 0 && e.offsetHeight === 0 || !v.support.reliableHiddenOffsets && (e.style && e.style.display || Dt(e, "display")) === "none"
    }, v.expr.filters.visible = function (e) {
        return !v.expr.filters.hidden(e)
    }), v.each({margin: "", padding: "", border: "Width"}, function (e, t) {
        v.cssHooks[e + t] = {
            expand: function (n) {
                var r, i = typeof n == "string" ? n.split(" ") : [n], s = {};
                for (r = 0; r < 4; r++) {
                    s[e + $t[r] + t] = i[r] || i[r - 2] || i[0]
                }
                return s
            }
        }, qt.test(e) || (v.cssHooks[e + t].set = Zt)
    });
    var rn = /%20/g, sn = /\[\]$/, on = /\r?\n/g,
        un = /^(?:color|date|datetime|datetime-local|email|hidden|month|number|password|range|search|tel|text|time|url|week)$/i,
        an = /^(?:select|textarea)/i;
    v.fn.extend({
        serialize: function () {
            return v.param(this.serializeArray())
        }, serializeArray: function () {
            return this.map(function () {
                return this.elements ? v.makeArray(this.elements) : this
            }).filter(function () {
                return this.name && !this.disabled && (this.checked || an.test(this.nodeName) || un.test(this.type))
            }).map(function (e, t) {
                var n = v(this).val();
                return n == null ? null : v.isArray(n) ? v.map(n, function (e, n) {
                    return {name: t.name, value: e.replace(on, "\r\n")}
                }) : {name: t.name, value: n.replace(on, "\r\n")}
            }).get()
        }
    }), v.param = function (e, n) {
        var r, i = [], s = function (e, t) {
            t = v.isFunction(t) ? t() : t == null ? "" : t, i[i.length] = encodeURIComponent(e) + "=" + encodeURIComponent(t)
        };
        n === t && (n = v.ajaxSettings && v.ajaxSettings.traditional);
        if (v.isArray(e) || e.jquery && !v.isPlainObject(e)) {
            v.each(e, function () {
                s(this.name, this.value)
            })
        } else {
            for (r in e) {
                fn(r, e[r], n, s)
            }
        }
        return i.join("&").replace(rn, "+")
    };
    var ln, cn, hn = /#.*$/, pn = /^(.*?):[ \t]*([^\r\n]*)\r?$/mg,
        dn = /^(?:about|app|app\-storage|.+\-extension|file|res|widget):$/, vn = /^(?:GET|HEAD)$/, mn = /^\/\//,
        gn = /\?/, yn = /<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi, bn = /([?&])_=[^&]*/,
        wn = /^([\w\+\.\-]+:)(?:\/\/([^\/?#:]*)(?::(\d+)|)|)/, En = v.fn.load, Sn = {}, xn = {}, Tn = ["*/"] + ["*"];
    try {
        cn = s.href
    } catch (Nn) {
        cn = i.createElement("a"), cn.href = "", cn = cn.href
    }
    ln = wn.exec(cn.toLowerCase()) || [], v.fn.load = function (e, n, r) {
        if (typeof e != "string" && En) {
            return En.apply(this, arguments)
        }
        if (!this.length) {
            return this
        }
        var i, s, o, u = this, a = e.indexOf(" ");
        return a >= 0 && (i = e.slice(a, e.length), e = e.slice(0, a)), v.isFunction(n) ? (r = n, n = t) : n && typeof n == "object" && (s = "POST"), v.ajax({
            url: e,
            type: s,
            dataType: "html",
            data: n,
            complete: function (e, t) {
                r && u.each(r, o || [e.responseText, t, e])
            }
        }).done(function (e) {
            o = arguments, u.html(i ? v("<div>").append(e.replace(yn, "")).find(i) : e)
        }), this
    }, v.each("ajaxStart ajaxStop ajaxComplete ajaxError ajaxSuccess ajaxSend".split(" "), function (e, t) {
        v.fn[t] = function (e) {
            return this.on(t, e)
        }
    }), v.each(["get", "post"], function (e, n) {
        v[n] = function (e, r, i, s) {
            return v.isFunction(r) && (s = s || i, i = r, r = t), v.ajax({
                type: n,
                url: e,
                data: r,
                success: i,
                dataType: s
            })
        }
    }), v.extend({
        getScript: function (e, n) {
            return v.get(e, t, n, "script")
        },
        getJSON: function (e, t, n) {
            return v.get(e, t, n, "json")
        },
        ajaxSetup: function (e, t) {
            return t ? Ln(e, v.ajaxSettings) : (t = e, e = v.ajaxSettings), Ln(e, t), e
        },
        ajaxSettings: {
            url: cn,
            isLocal: dn.test(ln[1]),
            global: !0,
            type: "GET",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            processData: !0,
            async: !0,
            accepts: {
                xml: "application/xml, text/xml",
                html: "text/html",
                text: "text/plain",
                json: "application/json, text/javascript",
                "*": Tn
            },
            contents: {xml: /xml/, html: /html/, json: /json/},
            responseFields: {xml: "responseXML", text: "responseText"},
            converters: {"* text": e.String, "text html": !0, "text json": v.parseJSON, "text xml": v.parseXML},
            flatOptions: {context: !0, url: !0}
        },
        ajaxPrefilter: Cn(Sn),
        ajaxTransport: Cn(xn),
        ajax: function (e, n) {
            function T(e, n, s, a) {
                var l, y, b, w, S, T = n;
                if (E === 2) {
                    return
                }
                E = 2, u && clearTimeout(u), o = t, i = a || "", x.readyState = e > 0 ? 4 : 0, s && (w = An(c, x, s));
                if (e >= 200 && e < 300 || e === 304) {
                    c.ifModified && (S = x.getResponseHeader("Last-Modified"), S && (v.lastModified[r] = S), S = x.getResponseHeader("Etag"), S && (v.etag[r] = S)), e === 304 ? (T = "notmodified", l = !0) : (l = On(c, w), T = l.state, y = l.data, b = l.error, l = !b)
                } else {
                    b = T;
                    if (!T || e) {
                        T = "error", e < 0 && (e = 0)
                    }
                }
                x.status = e, x.statusText = (n || T) + "", l ? d.resolveWith(h, [y, T, x]) : d.rejectWith(h, [x, T, b]), x.statusCode(g), g = t, f && p.trigger("ajax" + (l ? "Success" : "Error"), [x, c, l ? y : b]), m.fireWith(h, [x, T]), f && (p.trigger("ajaxComplete", [x, c]), --v.active || v.event.trigger("ajaxStop"))
            }

            typeof e == "object" && (n = e, e = t), n = n || {};
            var r, i, s, o, u, a, f, l, c = v.ajaxSetup({}, n), h = c.context || c,
                p = h !== c && (h.nodeType || h instanceof v) ? v(h) : v.event, d = v.Deferred(),
                m = v.Callbacks("once memory"), g = c.statusCode || {}, b = {}, w = {}, E = 0, S = "canceled", x = {
                    readyState: 0, setRequestHeader: function (e, t) {
                        if (!E) {
                            var n = e.toLowerCase();
                            e = w[n] = w[n] || e, b[e] = t
                        }
                        return this
                    }, getAllResponseHeaders: function () {
                        return E === 2 ? i : null
                    }, getResponseHeader: function (e) {
                        var n;
                        if (E === 2) {
                            if (!s) {
                                s = {};
                                while (n = pn.exec(i)) {
                                    s[n[1].toLowerCase()] = n[2]
                                }
                            }
                            n = s[e.toLowerCase()]
                        }
                        return n === t ? null : n
                    }, overrideMimeType: function (e) {
                        return E || (c.mimeType = e), this
                    }, abort: function (e) {
                        return e = e || S, o && o.abort(e), T(0, e), this
                    }
                };
            d.promise(x), x.success = x.done, x.error = x.fail, x.complete = m.add, x.statusCode = function (e) {
                if (e) {
                    var t;
                    if (E < 2) {
                        for (t in e) {
                            g[t] = [g[t], e[t]]
                        }
                    } else {
                        t = e[x.status], x.always(t)
                    }
                }
                return this
            }, c.url = ((e || c.url) + "").replace(hn, "").replace(mn, ln[1] + "//"), c.dataTypes = v.trim(c.dataType || "*").toLowerCase().split(y), c.crossDomain == null && (a = wn.exec(c.url.toLowerCase()), c.crossDomain = !(!a || a[1] === ln[1] && a[2] === ln[2] && (a[3] || (a[1] === "http:" ? 80 : 443)) == (ln[3] || (ln[1] === "http:" ? 80 : 443)))), c.data && c.processData && typeof c.data != "string" && (c.data = v.param(c.data, c.traditional)), kn(Sn, c, n, x);
            if (E === 2) {
                return x
            }
            f = c.global, c.type = c.type.toUpperCase(), c.hasContent = !vn.test(c.type), f && v.active++ === 0 && v.event.trigger("ajaxStart");
            if (!c.hasContent) {
                c.data && (c.url += (gn.test(c.url) ? "&" : "?") + c.data, delete c.data), r = c.url;
                if (c.cache === !1) {
                    var N = v.now(), C = c.url.replace(bn, "$1_=" + N);
                    c.url = C + (C === c.url ? (gn.test(c.url) ? "&" : "?") + "_=" + N : "")
                }
            }
            (c.data && c.hasContent && c.contentType !== !1 || n.contentType) && x.setRequestHeader("Content-Type", c.contentType), c.ifModified && (r = r || c.url, v.lastModified[r] && x.setRequestHeader("If-Modified-Since", v.lastModified[r]), v.etag[r] && x.setRequestHeader("If-None-Match", v.etag[r])), x.setRequestHeader("Accept", c.dataTypes[0] && c.accepts[c.dataTypes[0]] ? c.accepts[c.dataTypes[0]] + (c.dataTypes[0] !== "*" ? ", " + Tn + "; q=0.01" : "") : c.accepts["*"]);
            for (l in c.headers) {
                x.setRequestHeader(l, c.headers[l])
            }
            if (!c.beforeSend || c.beforeSend.call(h, x, c) !== !1 && E !== 2) {
                S = "abort";
                for (l in {success: 1, error: 1, complete: 1}) {
                    x[l](c[l])
                }
                o = kn(xn, c, n, x);
                if (!o) {
                    T(-1, "No Transport")
                } else {
                    x.readyState = 1, f && p.trigger("ajaxSend", [x, c]), c.async && c.timeout > 0 && (u = setTimeout(function () {
                        x.abort("timeout")
                    }, c.timeout));
                    try {
                        E = 1, o.send(b, T)
                    } catch (k) {
                        if (!(E < 2)) {
                            throw k
                        }
                        T(-1, k)
                    }
                }
                return x
            }
            return x.abort()
        },
        active: 0,
        lastModified: {},
        etag: {}
    });
    var Mn = [], _n = /\?/, Dn = /(=)\?(?=&|$)|\?\?/, Pn = v.now();
    v.ajaxSetup({
        jsonp: "callback", jsonpCallback: function () {
            var e = Mn.pop() || v.expando + "_" + Pn++;
            return this[e] = !0, e
        }
    }), v.ajaxPrefilter("json jsonp", function (n, r, i) {
        var s, o, u, a = n.data, f = n.url, l = n.jsonp !== !1, c = l && Dn.test(f),
            h = l && !c && typeof a == "string" && !(n.contentType || "").indexOf("application/x-www-form-urlencoded") && Dn.test(a);
        if (n.dataTypes[0] === "jsonp" || c || h) {
            return s = n.jsonpCallback = v.isFunction(n.jsonpCallback) ? n.jsonpCallback() : n.jsonpCallback, o = e[s], c ? n.url = f.replace(Dn, "$1" + s) : h ? n.data = a.replace(Dn, "$1" + s) : l && (n.url += (_n.test(f) ? "&" : "?") + n.jsonp + "=" + s), n.converters["script json"] = function () {
                return u || v.error(s + " was not called"), u[0]
            }, n.dataTypes[0] = "json", e[s] = function () {
                u = arguments
            }, i.always(function () {
                e[s] = o, n[s] && (n.jsonpCallback = r.jsonpCallback, Mn.push(s)), u && v.isFunction(o) && o(u[0]), u = o = t
            }), "script"
        }
    }), v.ajaxSetup({
        accepts: {script: "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript"},
        contents: {script: /javascript|ecmascript/},
        converters: {
            "text script": function (e) {
                return v.globalEval(e), e
            }
        }
    }), v.ajaxPrefilter("script", function (e) {
        e.cache === t && (e.cache = !1), e.crossDomain && (e.type = "GET", e.global = !1)
    }), v.ajaxTransport("script", function (e) {
        if (e.crossDomain) {
            var n, r = i.head || i.getElementsByTagName("head")[0] || i.documentElement;
            return {
                send: function (s, o) {
                    n = i.createElement("script"), n.async = "async", e.scriptCharset && (n.charset = e.scriptCharset), n.src = e.url, n.onload = n.onreadystatechange = function (e, i) {
                        if (i || !n.readyState || /loaded|complete/.test(n.readyState)) {
                            n.onload = n.onreadystatechange = null, r && n.parentNode && r.removeChild(n), n = t, i || o(200, "success")
                        }
                    }, r.insertBefore(n, r.firstChild)
                }, abort: function () {
                    n && n.onload(0, 1)
                }
            }
        }
    });
    var Hn, Bn = e.ActiveXObject ? function () {
        for (var e in Hn) {
            Hn[e](0, 1)
        }
    } : !1, jn = 0;
    v.ajaxSettings.xhr = e.ActiveXObject ? function () {
        return !this.isLocal && Fn() || In()
    } : Fn, function (e) {
        v.extend(v.support, {ajax: !!e, cors: !!e && "withCredentials" in e})
    }(v.ajaxSettings.xhr()), v.support.ajax && v.ajaxTransport(function (n) {
        if (!n.crossDomain || v.support.cors) {
            var r;
            return {
                send: function (i, s) {
                    var o, u, a = n.xhr();
                    n.username ? a.open(n.type, n.url, n.async, n.username, n.password) : a.open(n.type, n.url, n.async);
                    if (n.xhrFields) {
                        for (u in n.xhrFields) {
                            a[u] = n.xhrFields[u]
                        }
                    }
                    n.mimeType && a.overrideMimeType && a.overrideMimeType(n.mimeType), !n.crossDomain && !i["X-Requested-With"] && (i["X-Requested-With"] = "XMLHttpRequest");
                    try {
                        for (u in i) {
                            a.setRequestHeader(u, i[u])
                        }
                    } catch (f) {
                    }
                    a.send(n.hasContent && n.data || null), r = function (e, i) {
                        var u, f, l, c, h;
                        try {
                            if (r && (i || a.readyState === 4)) {
                                r = t, o && (a.onreadystatechange = v.noop, Bn && delete Hn[o]);
                                if (i) {
                                    a.readyState !== 4 && a.abort()
                                } else {
                                    u = a.status, l = a.getAllResponseHeaders(), c = {}, h = a.responseXML, h && h.documentElement && (c.xml = h);
                                    try {
                                        c.text = a.responseText
                                    } catch (p) {
                                    }
                                    try {
                                        f = a.statusText
                                    } catch (p) {
                                        f = ""
                                    }
                                    !u && n.isLocal && !n.crossDomain ? u = c.text ? 200 : 404 : u === 1223 && (u = 204)
                                }
                            }
                        } catch (d) {
                            i || s(-1, d)
                        }
                        c && s(u, f, c, l)
                    }, n.async ? a.readyState === 4 ? setTimeout(r, 0) : (o = ++jn, Bn && (Hn || (Hn = {}, v(e).unload(Bn)), Hn[o] = r), a.onreadystatechange = r) : r()
                }, abort: function () {
                    r && r(0, 1)
                }
            }
        }
    });
    var qn, Rn, Un = /^(?:toggle|show|hide)$/, zn = new RegExp("^(?:([-+])=|)(" + m + ")([a-z%]*)$", "i"),
        Wn = /queueHooks$/, Xn = [Gn], Vn = {
            "*": [function (e, t) {
                var n, r, i = this.createTween(e, t), s = zn.exec(t), o = i.cur(), u = +o || 0, a = 1, f = 20;
                if (s) {
                    n = +s[2], r = s[3] || (v.cssNumber[e] ? "" : "px");
                    if (r !== "px" && u) {
                        u = v.css(i.elem, e, !0) || n || 1;
                        do {
                            a = a || ".5", u /= a, v.style(i.elem, e, u + r)
                        } while (a !== (a = i.cur() / o) && a !== 1 && --f)
                    }
                    i.unit = r, i.start = u, i.end = s[1] ? u + (s[1] + 1) * n : n
                }
                return i
            }]
        };
    v.Animation = v.extend(Kn, {
        tweener: function (e, t) {
            v.isFunction(e) ? (t = e, e = ["*"]) : e = e.split(" ");
            var n, r = 0, i = e.length;
            for (; r < i; r++) {
                n = e[r], Vn[n] = Vn[n] || [], Vn[n].unshift(t)
            }
        }, prefilter: function (e, t) {
            t ? Xn.unshift(e) : Xn.push(e)
        }
    }), v.Tween = Yn, Yn.prototype = {
        constructor: Yn, init: function (e, t, n, r, i, s) {
            this.elem = e, this.prop = n, this.easing = i || "swing", this.options = t, this.start = this.now = this.cur(), this.end = r, this.unit = s || (v.cssNumber[n] ? "" : "px")
        }, cur: function () {
            var e = Yn.propHooks[this.prop];
            return e && e.get ? e.get(this) : Yn.propHooks._default.get(this)
        }, run: function (e) {
            var t, n = Yn.propHooks[this.prop];
            return this.options.duration ? this.pos = t = v.easing[this.easing](e, this.options.duration * e, 0, 1, this.options.duration) : this.pos = t = e, this.now = (this.end - this.start) * t + this.start, this.options.step && this.options.step.call(this.elem, this.now, this), n && n.set ? n.set(this) : Yn.propHooks._default.set(this), this
        }
    }, Yn.prototype.init.prototype = Yn.prototype, Yn.propHooks = {
        _default: {
            get: function (e) {
                var t;
                return e.elem[e.prop] == null || !!e.elem.style && e.elem.style[e.prop] != null ? (t = v.css(e.elem, e.prop, !1, ""), !t || t === "auto" ? 0 : t) : e.elem[e.prop]
            }, set: function (e) {
                v.fx.step[e.prop] ? v.fx.step[e.prop](e) : e.elem.style && (e.elem.style[v.cssProps[e.prop]] != null || v.cssHooks[e.prop]) ? v.style(e.elem, e.prop, e.now + e.unit) : e.elem[e.prop] = e.now
            }
        }
    }, Yn.propHooks.scrollTop = Yn.propHooks.scrollLeft = {
        set: function (e) {
            e.elem.nodeType && e.elem.parentNode && (e.elem[e.prop] = e.now)
        }
    }, v.each(["toggle", "show", "hide"], function (e, t) {
        var n = v.fn[t];
        v.fn[t] = function (r, i, s) {
            return r == null || typeof r == "boolean" || !e && v.isFunction(r) && v.isFunction(i) ? n.apply(this, arguments) : this.animate(Zn(t, !0), r, i, s)
        }
    }), v.fn.extend({
        fadeTo: function (e, t, n, r) {
            return this.filter(Gt).css("opacity", 0).show().end().animate({opacity: t}, e, n, r)
        }, animate: function (e, t, n, r) {
            var i = v.isEmptyObject(e), s = v.speed(t, n, r), o = function () {
                var t = Kn(this, v.extend({}, e), s);
                i && t.stop(!0)
            };
            return i || s.queue === !1 ? this.each(o) : this.queue(s.queue, o)
        }, stop: function (e, n, r) {
            var i = function (e) {
                var t = e.stop;
                delete e.stop, t(r)
            };
            return typeof e != "string" && (r = n, n = e, e = t), n && e !== !1 && this.queue(e || "fx", []), this.each(function () {
                var t = !0, n = e != null && e + "queueHooks", s = v.timers, o = v._data(this);
                if (n) {
                    o[n] && o[n].stop && i(o[n])
                } else {
                    for (n in o) {
                        o[n] && o[n].stop && Wn.test(n) && i(o[n])
                    }
                }
                for (n = s.length; n--;) {
                    s[n].elem === this && (e == null || s[n].queue === e) && (s[n].anim.stop(r), t = !1, s.splice(n, 1))
                }
                (t || !r) && v.dequeue(this, e)
            })
        }
    }), v.each({
        slideDown: Zn("show"),
        slideUp: Zn("hide"),
        slideToggle: Zn("toggle"),
        fadeIn: {opacity: "show"},
        fadeOut: {opacity: "hide"},
        fadeToggle: {opacity: "toggle"}
    }, function (e, t) {
        v.fn[e] = function (e, n, r) {
            return this.animate(t, e, n, r)
        }
    }), v.speed = function (e, t, n) {
        var r = e && typeof e == "object" ? v.extend({}, e) : {
            complete: n || !n && t || v.isFunction(e) && e,
            duration: e,
            easing: n && t || t && !v.isFunction(t) && t
        };
        r.duration = v.fx.off ? 0 : typeof r.duration == "number" ? r.duration : r.duration in v.fx.speeds ? v.fx.speeds[r.duration] : v.fx.speeds._default;
        if (r.queue == null || r.queue === !0) {
            r.queue = "fx"
        }
        return r.old = r.complete, r.complete = function () {
            v.isFunction(r.old) && r.old.call(this), r.queue && v.dequeue(this, r.queue)
        }, r
    }, v.easing = {
        linear: function (e) {
            return e
        }, swing: function (e) {
            return 0.5 - Math.cos(e * Math.PI) / 2
        }
    }, v.timers = [], v.fx = Yn.prototype.init, v.fx.tick = function () {
        var e, n = v.timers, r = 0;
        qn = v.now();
        for (; r < n.length; r++) {
            e = n[r], !e() && n[r] === e && n.splice(r--, 1)
        }
        n.length || v.fx.stop(), qn = t
    }, v.fx.timer = function (e) {
        e() && v.timers.push(e) && !Rn && (Rn = setInterval(v.fx.tick, v.fx.interval))
    }, v.fx.interval = 13, v.fx.stop = function () {
        clearInterval(Rn), Rn = null
    }, v.fx.speeds = {
        slow: 600,
        fast: 200,
        _default: 400
    }, v.fx.step = {}, v.expr && v.expr.filters && (v.expr.filters.animated = function (e) {
        return v.grep(v.timers, function (t) {
            return e === t.elem
        }).length
    });
    var er = /^(?:body|html)$/i;
    v.fn.offset = function (e) {
        if (arguments.length) {
            return e === t ? this : this.each(function (t) {
                v.offset.setOffset(this, e, t)
            })
        }
        var n, r, i, s, o, u, a, f = {top: 0, left: 0}, l = this[0], c = l && l.ownerDocument;
        if (!c) {
            return
        }
        return (r = c.body) === l ? v.offset.bodyOffset(l) : (n = c.documentElement, v.contains(n, l) ? (typeof l.getBoundingClientRect != "undefined" && (f = l.getBoundingClientRect()), i = tr(c), s = n.clientTop || r.clientTop || 0, o = n.clientLeft || r.clientLeft || 0, u = i.pageYOffset || n.scrollTop, a = i.pageXOffset || n.scrollLeft, {
            top: f.top + u - s,
            left: f.left + a - o
        }) : f)
    }, v.offset = {
        bodyOffset: function (e) {
            var t = e.offsetTop, n = e.offsetLeft;
            return v.support.doesNotIncludeMarginInBodyOffset && (t += parseFloat(v.css(e, "marginTop")) || 0, n += parseFloat(v.css(e, "marginLeft")) || 0), {
                top: t,
                left: n
            }
        }, setOffset: function (e, t, n) {
            var r = v.css(e, "position");
            r === "static" && (e.style.position = "relative");
            var i = v(e), s = i.offset(), o = v.css(e, "top"), u = v.css(e, "left"),
                a = (r === "absolute" || r === "fixed") && v.inArray("auto", [o, u]) > -1, f = {}, l = {}, c, h;
            a ? (l = i.position(), c = l.top, h = l.left) : (c = parseFloat(o) || 0, h = parseFloat(u) || 0), v.isFunction(t) && (t = t.call(e, n, s)), t.top != null && (f.top = t.top - s.top + c), t.left != null && (f.left = t.left - s.left + h), "using" in t ? t.using.call(e, f) : i.css(f)
        }
    }, v.fn.extend({
        position: function () {
            if (!this[0]) {
                return
            }
            var e = this[0], t = this.offsetParent(), n = this.offset(),
                r = er.test(t[0].nodeName) ? {top: 0, left: 0} : t.offset();
            return n.top -= parseFloat(v.css(e, "marginTop")) || 0, n.left -= parseFloat(v.css(e, "marginLeft")) || 0, r.top += parseFloat(v.css(t[0], "borderTopWidth")) || 0, r.left += parseFloat(v.css(t[0], "borderLeftWidth")) || 0, {
                top: n.top - r.top,
                left: n.left - r.left
            }
        }, offsetParent: function () {
            return this.map(function () {
                var e = this.offsetParent || i.body;
                while (e && !er.test(e.nodeName) && v.css(e, "position") === "static") {
                    e = e.offsetParent
                }
                return e || i.body
            })
        }
    }), v.each({scrollLeft: "pageXOffset", scrollTop: "pageYOffset"}, function (e, n) {
        var r = /Y/.test(n);
        v.fn[e] = function (i) {
            return v.access(this, function (e, i, s) {
                var o = tr(e);
                if (s === t) {
                    return o ? n in o ? o[n] : o.document.documentElement[i] : e[i]
                }
                o ? o.scrollTo(r ? v(o).scrollLeft() : s, r ? s : v(o).scrollTop()) : e[i] = s
            }, e, i, arguments.length, null)
        }
    }), v.each({Height: "height", Width: "width"}, function (e, n) {
        v.each({padding: "inner" + e, content: n, "": "outer" + e}, function (r, i) {
            v.fn[i] = function (i, s) {
                var o = arguments.length && (r || typeof i != "boolean"),
                    u = r || (i === !0 || s === !0 ? "margin" : "border");
                return v.access(this, function (n, r, i) {
                    var s;
                    return v.isWindow(n) ? n.document.documentElement["client" + e] : n.nodeType === 9 ? (s = n.documentElement, Math.max(n.body["scroll" + e], s["scroll" + e], n.body["offset" + e], s["offset" + e], s["client" + e])) : i === t ? v.css(n, r, i, u) : v.style(n, r, i, u)
                }, n, o ? i : t, o, null)
            }
        })
    }), e.jQuery = e.$ = v, typeof define == "function" && define.amd && define.amd.jQuery && define("jquery", [], function () {
        return v
    })
})(window);
;
/*!art-template - Template Engine | http://aui.github.com/artTemplate/*/
!function () {
    function Z(b) {
        return b.replace(G, "").replace(F, ",").replace(E, "").replace(D, "").replace(C, "").split(B)
    }

    function Y(b) {
        return "'" + b.replace(/('|\\)/g, "\\$1").replace(/\r/g, "\\r").replace(/\n/g, "\\n") + "'"
    }

    function X(ar, aq) {
        function ap(c) {
            return ah += c.split(/\n/).length - 1, aj && (c = c.replace(/\s+/g, " ").replace(/<!--[\w\W]*?-->/g, "")), c && (c = ac[1] + Y(c) + ac[2] + "\n"), c
        }

        function ao(d) {
            var i = ah;
            if (ak ? d = ak(d, aq) : an && (d = d.replace(/\n/g, function () {
                return ah++, "$line=" + ah + ";"
            })), 0 === d.indexOf("=")) {
                var h = ai && !/^=[=#]/.test(d);
                if (d = d.replace(/^=[=#]?|[\s;]*$/g, ""), h) {
                    var g = d.replace(/\s*\([^\)]+\)/, "");
                    M[g] || /^(include|print)$/.test(g) || (d = "$escape(" + d + ")")
                } else {
                    d = "$string(" + d + ")"
                }
                d = ac[1] + d + ac[2]
            }
            return an && (d = "$line=" + i + ";" + d), I(Z(d), function (e) {
                if (e && !ag[e]) {
                    var c;
                    c = "print" === e ? aa : "include" === e ? r : M[e] ? "$utils." + e : L[e] ? "$helpers." + e : "$data." + e, o += e + "=" + c + ",", ag[e] = !0
                }
            }), d + "\n"
        }

        var an = aq.debug, am = aq.openTag, al = aq.closeTag, ak = aq.parser, aj = aq.compress, ai = aq.escape, ah = 1,
            ag = {$data: 1, $filename: 1, $utils: 1, $helpers: 1, $out: 1, $line: 1}, ae = "".trim,
            ac = ae ? ["$out='';", "$out+=", ";", "$out"] : ["$out=[];", "$out.push(", ");", "$out.join('')"],
            ab = ae ? "$out+=text;return $out;" : "$out.push(text);",
            aa = "function(){var text=''.concat.apply('',arguments);" + ab + "}",
            r = "function(filename,data){data=data||$data;var text=$utils.$include(filename,data,$filename);" + ab + "}",
            o = "'use strict';var $utils=this,$helpers=$utils.$helpers," + (an ? "$line=0," : ""), n = ac[0],
            b = "return new String(" + ac[3] + ");";
        I(ar.split(am), function (e) {
            e = e.split(al);
            var d = e[0], f = e[1];
            1 === e.length ? n += ap(d) : (n += ao(d), f && (n += ap(f)))
        });
        var a = o + n + b;
        an && (a = "try{" + a + "}catch(e){throw {filename:$filename,name:'Render Error',message:e.message,line:$line,source:" + Y(ar) + ".split(/\\n/)[$line-1].replace(/^\\s+/,'')};}");
        try {
            var af = new Function("$data", "$filename", a);
            return af.prototype = M, af
        } catch (ad) {
            throw ad.temp = "function anonymous($data,$filename) {" + a + "}", ad
        }
    }

    var W = function (d, c) {
        return "string" == typeof c ? J(c, {filename: d}) : T(d, c)
    };
    W.version = "3.0.0", W.config = function (d, c) {
        V[d] = c
    };
    var V = W.defaults = {openTag: "<%", closeTag: "%>", escape: !0, cache: !0, compress: !1, parser: null},
        U = W.cache = {};
    W.render = function (d, c) {
        return J(d, c)
    };
    var T = W.renderFile = function (e, d) {
        var f = W.get(e) || K({filename: e, name: "Render Error", message: "Template not found"});
        return d ? f(d) : f
    };
    W.get = function (f) {
        var e;
        if (U[f]) {
            e = U[f]
        } else {
            if ("object" == typeof document) {
                var h = document.getElementById(f);
                if (h) {
                    var g = (h.value || h.innerHTML).replace(/^\s*|\s*$/g, "");
                    e = J(g, {filename: f})
                }
            }
        }
        return e
    };
    var S = function (d, c) {
        return "string" != typeof d && (c = typeof d, "number" === c ? d += "" : d = "function" === c ? S(d.call(d)) : ""), d
    }, R = {"<": "&#60;", ">": "&#62;", '"': "&#34;", "'": "&#39;", "&": "&#38;"}, Q = function (b) {
        return R[b]
    }, P = function (b) {
        return S(b).replace(/&(?![\w#]+;)|[<>"']/g, Q)
    }, O = Array.isArray || function (b) {
        return "[object Array]" === {}.toString.call(b)
    }, N = function (f, e) {
        var h, g;
        if (O(f)) {
            for (h = 0, g = f.length; g > h; h++) {
                e.call(f, f[h], h, f)
            }
        } else {
            for (h in f) {
                e.call(f, f[h], h)
            }
        }
    }, M = W.utils = {$helpers: {}, $include: T, $string: S, $escape: P, $each: N};
    W.helper = function (d, c) {
        L[d] = c
    };
    var L = W.helpers = M.$helpers;
    W.onerror = function (e) {
        var d = "Template Error\n\n";
        for (var f in e) {
            d += "<" + f + ">\n" + e[f] + "\n\n"
        }
        "object" == typeof console && console.error(d)
    };
    var K = function (b) {
            return W.onerror(b), function () {
                return "{Template Error}"
            }
        }, J = W.compile = function (e, c) {
            function n(b) {
                try {
                    return new k(b, l) + ""
                } catch (a) {
                    return c.debug ? K(a)() : (c.debug = !0, J(e, c)(b))
                }
            }

            c = c || {};
            for (var m in V) {
                void 0 === c[m] && (c[m] = V[m])
            }
            var l = c.filename;
            try {
                var k = X(e, c)
            } catch (f) {
                return f.filename = l || "anonymous", f.name = "Syntax Error", K(f)
            }
            return n.prototype = k.prototype, n.toString = function () {
                return k.toString()
            }, l && c.cache && (U[l] = n), n
        }, I = M.$each,
        H = "break,case,catch,continue,debugger,default,delete,do,else,false,finally,for,function,if,in,instanceof,new,null,return,switch,this,throw,true,try,typeof,var,void,while,with,abstract,boolean,byte,char,class,const,double,enum,export,extends,final,float,goto,implements,import,int,interface,long,native,package,private,protected,public,short,static,super,synchronized,throws,transient,volatile,arguments,let,yield,undefined",
        G = /\/\*[\w\W]*?\*\/|\/\/[^\n]*\n|\/\/[^\n]*$|"(?:[^"\\]|\\[\w\W])*"|'(?:[^'\\]|\\[\w\W])*'|\s*\.\s*[$\w\.]+/g,
        F = /[^\w$]+/g, E = new RegExp(["\\b" + H.replace(/,/g, "\\b|\\b") + "\\b"].join("|"), "g"),
        D = /^\d[^,]*|,\d[^,]*/g, C = /^,+|,+$/g, B = /^$|,+/;
    V.openTag = "{{", V.closeTag = "}}";
    var A = function (g, f) {
        var j = f.split(":"), i = j.shift(), h = j.join(":") || "";
        return h && (h = ", " + h), "$helpers." + i + "(" + g + h + ")"
    };
    V.parser = function (ab) {
        ab = ab.replace(/^\s/, "");
        var aa = ab.split(" "), z = aa.shift(), y = aa.join(" ");
        switch (z) {
            case"if":
                ab = "if(" + y + "){";
                break;
            case"else":
                aa = "if" === aa.shift() ? " if(" + aa.join(" ") + ")" : "", ab = "}else" + aa + "{";
                break;
            case"/if":
                ab = "}";
                break;
            case"each":
                var x = aa[0] || "$data", w = aa[1] || "as", v = aa[2] || "$value", u = aa[3] || "$index",
                    t = v + "," + u;
                "as" !== w && (x = "[]"), ab = "$each(" + x + ",function(" + t + "){";
                break;
            case"/each":
                ab = "});";
                break;
            case"echo":
                ab = "print(" + y + ");";
                break;
            case"print":
            case"include":
                ab = z + "(" + aa.join(",") + ");";
                break;
            default:
                if (/^\s*\|\s*[\w\$]/.test(y)) {
                    var s = !0;
                    0 === ab.indexOf("#") && (ab = ab.substr(1), s = !1);
                    for (var r = 0, q = ab.split("|"), p = q.length, d = q[r++]; p > r; r++) {
                        d = A(d, q[r])
                    }
                    ab = (s ? "=" : "=#") + d
                } else {
                    ab = W.helpers[z] ? "=#" + z + "(" + aa.join(",") + ");" : "=" + ab
                }
        }
        return ab
    }, "function" == typeof define ? define(function () {
        return W
    }) : "undefined" != typeof exports ? module.exports = W : this.template = W
}();
;
var hexcase = 0;
var b64pad = "";
var chrsz = 8;

function hex_md5(a) {
    return binl2hex(core_md5(str2binl(a), a.length * chrsz))
}

function b64_md5(a) {
    return binl2b64(core_md5(str2binl(a), a.length * chrsz))
}

function str_md5(a) {
    return binl2str(core_md5(str2binl(a), a.length * chrsz))
}

function hex_hmac_md5(a, b) {
    return binl2hex(core_hmac_md5(a, b))
}

function b64_hmac_md5(a, b) {
    return binl2b64(core_hmac_md5(a, b))
}

function str_hmac_md5(a, b) {
    return binl2str(core_hmac_md5(a, b))
}

function md5_vm_test() {
    return hex_md5("abc") == "900150983cd24fb0d6963f7d28e17f72"
}

function core_md5(p, k) {
    p[k >> 5] |= 128 << ((k) % 32);
    p[(((k + 64) >>> 9) << 4) + 14] = k;
    var o = 1732584193;
    var n = -271733879;
    var m = -1732584194;
    var l = 271733878;
    for (var g = 0; g < p.length; g += 16) {
        var j = o;
        var h = n;
        var f = m;
        var e = l;
        o = md5_ff(o, n, m, l, p[g + 0], 7, -680876936);
        l = md5_ff(l, o, n, m, p[g + 1], 12, -389564586);
        m = md5_ff(m, l, o, n, p[g + 2], 17, 606105819);
        n = md5_ff(n, m, l, o, p[g + 3], 22, -1044525330);
        o = md5_ff(o, n, m, l, p[g + 4], 7, -176418897);
        l = md5_ff(l, o, n, m, p[g + 5], 12, 1200080426);
        m = md5_ff(m, l, o, n, p[g + 6], 17, -1473231341);
        n = md5_ff(n, m, l, o, p[g + 7], 22, -45705983);
        o = md5_ff(o, n, m, l, p[g + 8], 7, 1770035416);
        l = md5_ff(l, o, n, m, p[g + 9], 12, -1958414417);
        m = md5_ff(m, l, o, n, p[g + 10], 17, -42063);
        n = md5_ff(n, m, l, o, p[g + 11], 22, -1990404162);
        o = md5_ff(o, n, m, l, p[g + 12], 7, 1804603682);
        l = md5_ff(l, o, n, m, p[g + 13], 12, -40341101);
        m = md5_ff(m, l, o, n, p[g + 14], 17, -1502002290);
        n = md5_ff(n, m, l, o, p[g + 15], 22, 1236535329);
        o = md5_gg(o, n, m, l, p[g + 1], 5, -165796510);
        l = md5_gg(l, o, n, m, p[g + 6], 9, -1069501632);
        m = md5_gg(m, l, o, n, p[g + 11], 14, 643717713);
        n = md5_gg(n, m, l, o, p[g + 0], 20, -373897302);
        o = md5_gg(o, n, m, l, p[g + 5], 5, -701558691);
        l = md5_gg(l, o, n, m, p[g + 10], 9, 38016083);
        m = md5_gg(m, l, o, n, p[g + 15], 14, -660478335);
        n = md5_gg(n, m, l, o, p[g + 4], 20, -405537848);
        o = md5_gg(o, n, m, l, p[g + 9], 5, 568446438);
        l = md5_gg(l, o, n, m, p[g + 14], 9, -1019803690);
        m = md5_gg(m, l, o, n, p[g + 3], 14, -187363961);
        n = md5_gg(n, m, l, o, p[g + 8], 20, 1163531501);
        o = md5_gg(o, n, m, l, p[g + 13], 5, -1444681467);
        l = md5_gg(l, o, n, m, p[g + 2], 9, -51403784);
        m = md5_gg(m, l, o, n, p[g + 7], 14, 1735328473);
        n = md5_gg(n, m, l, o, p[g + 12], 20, -1926607734);
        o = md5_hh(o, n, m, l, p[g + 5], 4, -378558);
        l = md5_hh(l, o, n, m, p[g + 8], 11, -2022574463);
        m = md5_hh(m, l, o, n, p[g + 11], 16, 1839030562);
        n = md5_hh(n, m, l, o, p[g + 14], 23, -35309556);
        o = md5_hh(o, n, m, l, p[g + 1], 4, -1530992060);
        l = md5_hh(l, o, n, m, p[g + 4], 11, 1272893353);
        m = md5_hh(m, l, o, n, p[g + 7], 16, -155497632);
        n = md5_hh(n, m, l, o, p[g + 10], 23, -1094730640);
        o = md5_hh(o, n, m, l, p[g + 13], 4, 681279174);
        l = md5_hh(l, o, n, m, p[g + 0], 11, -358537222);
        m = md5_hh(m, l, o, n, p[g + 3], 16, -722521979);
        n = md5_hh(n, m, l, o, p[g + 6], 23, 76029189);
        o = md5_hh(o, n, m, l, p[g + 9], 4, -640364487);
        l = md5_hh(l, o, n, m, p[g + 12], 11, -421815835);
        m = md5_hh(m, l, o, n, p[g + 15], 16, 530742520);
        n = md5_hh(n, m, l, o, p[g + 2], 23, -995338651);
        o = md5_ii(o, n, m, l, p[g + 0], 6, -198630844);
        l = md5_ii(l, o, n, m, p[g + 7], 10, 1126891415);
        m = md5_ii(m, l, o, n, p[g + 14], 15, -1416354905);
        n = md5_ii(n, m, l, o, p[g + 5], 21, -57434055);
        o = md5_ii(o, n, m, l, p[g + 12], 6, 1700485571);
        l = md5_ii(l, o, n, m, p[g + 3], 10, -1894986606);
        m = md5_ii(m, l, o, n, p[g + 10], 15, -1051523);
        n = md5_ii(n, m, l, o, p[g + 1], 21, -2054922799);
        o = md5_ii(o, n, m, l, p[g + 8], 6, 1873313359);
        l = md5_ii(l, o, n, m, p[g + 15], 10, -30611744);
        m = md5_ii(m, l, o, n, p[g + 6], 15, -1560198380);
        n = md5_ii(n, m, l, o, p[g + 13], 21, 1309151649);
        o = md5_ii(o, n, m, l, p[g + 4], 6, -145523070);
        l = md5_ii(l, o, n, m, p[g + 11], 10, -1120210379);
        m = md5_ii(m, l, o, n, p[g + 2], 15, 718787259);
        n = md5_ii(n, m, l, o, p[g + 9], 21, -343485551);
        o = safe_add(o, j);
        n = safe_add(n, h);
        m = safe_add(m, f);
        l = safe_add(l, e)
    }
    return Array(o, n, m, l)
}

function md5_cmn(h, e, d, c, g, f) {
    return safe_add(bit_rol(safe_add(safe_add(e, h), safe_add(c, f)), g), d)
}

function md5_ff(g, f, k, j, e, i, h) {
    return md5_cmn((f & k) | ((~f) & j), g, f, e, i, h)
}

function md5_gg(g, f, k, j, e, i, h) {
    return md5_cmn((f & j) | (k & (~j)), g, f, e, i, h)
}

function md5_hh(g, f, k, j, e, i, h) {
    return md5_cmn(f ^ k ^ j, g, f, e, i, h)
}

function md5_ii(g, f, k, j, e, i, h) {
    return md5_cmn(k ^ (f | (~j)), g, f, e, i, h)
}

function core_hmac_md5(c, f) {
    var e = str2binl(c);
    if (e.length > 16) {
        e = core_md5(e, c.length * chrsz)
    }
    var a = Array(16), d = Array(16);
    for (var b = 0; b < 16; b++) {
        a[b] = e[b] ^ 909522486;
        d[b] = e[b] ^ 1549556828
    }
    var g = core_md5(a.concat(str2binl(f)), 512 + f.length * chrsz);
    return core_md5(d.concat(g), 512 + 128)
}

function safe_add(a, d) {
    var c = (a & 65535) + (d & 65535);
    var b = (a >> 16) + (d >> 16) + (c >> 16);
    return (b << 16) | (c & 65535)
}

function bit_rol(a, b) {
    return (a << b) | (a >>> (32 - b))
}

function str2binl(d) {
    var c = Array();
    var a = (1 << chrsz) - 1;
    for (var b = 0; b < d.length * chrsz; b += chrsz) {
        c[b >> 5] |= (d.charCodeAt(b / chrsz) & a) << (b % 32)
    }
    return c
}

function binl2str(c) {
    var d = "";
    var a = (1 << chrsz) - 1;
    for (var b = 0; b < c.length * 32; b += chrsz) {
        d += String.fromCharCode((c[b >> 5] >>> (b % 32)) & a)
    }
    return d
}

function binl2hex(c) {
    var b = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
    var d = "";
    for (var a = 0; a < c.length * 4; a++) {
        d += b.charAt((c[a >> 2] >> ((a % 4) * 8 + 4)) & 15) + b.charAt((c[a >> 2] >> ((a % 4) * 8)) & 15)
    }
    return d
}

function binl2b64(d) {
    var c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    var f = "";
    for (var b = 0; b < d.length * 4; b += 3) {
        var e = (((d[b >> 2] >> 8 * (b % 4)) & 255) << 16) | (((d[b + 1 >> 2] >> 8 * ((b + 1) % 4)) & 255) << 8) | ((d[b + 2 >> 2] >> 8 * ((b + 2) % 4)) & 255);
        for (var a = 0; a < 4; a++) {
            if (b * 8 + a * 6 > d.length * 32) {
                f += b64pad
            } else {
                f += c.charAt((e >> 6 * (3 - a)) & 63)
            }
        }
    }
    return f
};
;
/*!
 * jquery.base64.js 0.0.3 - https://github.com/yckart/jquery.base64.js
 * Makes Base64 en & -decoding simpler as it is.
 *
 * Based upon: https://gist.github.com/Yaffle/1284012
 *
 * Copyright (c) 2012 Yannick Albert (http://yckart.com)
 * Licensed under the MIT license (http://www.opensource.org/licenses/mit-license.php).
 * 2013/02/10
 **/
(function (f) {
    var b = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", l = "", j = [256], e = [256], g = 0;
    var d = {
        encode: function (c) {
            var i = c.replace(/[\u0080-\u07ff]/g, function (n) {
                var m = n.charCodeAt(0);
                return String.fromCharCode(192 | m >> 6, 128 | m & 63)
            }).replace(/[\u0800-\uffff]/g, function (n) {
                var m = n.charCodeAt(0);
                return String.fromCharCode(224 | m >> 12, 128 | m >> 6 & 63, 128 | m & 63)
            });
            return i
        }, decode: function (i) {
            var c = i.replace(/[\u00e0-\u00ef][\u0080-\u00bf][\u0080-\u00bf]/g, function (n) {
                var m = ((n.charCodeAt(0) & 15) << 12) | ((n.charCodeAt(1) & 63) << 6) | (n.charCodeAt(2) & 63);
                return String.fromCharCode(m)
            }).replace(/[\u00c0-\u00df][\u0080-\u00bf]/g, function (n) {
                var m = (n.charCodeAt(0) & 31) << 6 | n.charCodeAt(1) & 63;
                return String.fromCharCode(m)
            });
            return c
        }
    };
    while (g < 256) {
        var h = String.fromCharCode(g);
        l += h;
        e[g] = g;
        j[g] = b.indexOf(h);
        ++g
    }

    function a(z, u, n, x, t, r) {
        z = String(z);
        var o = 0, q = 0, m = z.length, y = "", w = 0;
        while (q < m) {
            var v = z.charCodeAt(q);
            v = v < 256 ? n[v] : -1;
            o = (o << t) + v;
            w += t;
            while (w >= r) {
                w -= r;
                var p = o >> w;
                y += x.charAt(p);
                o ^= p << w
            }
            ++q
        }
        if (!u && w > 0) {
            y += x.charAt(o << (r - w))
        }
        return y
    }

    var k = f.base64 = function (i, c, m) {
        return c ? k[i](c, m) : i ? null : this
    };
    k.btoa = k.encode = function (c, i) {
        c = k.raw === false || k.utf8encode || i ? d.encode(c) : c;
        c = a(c, false, e, b, 8, 6);
        return c + "====".slice((c.length % 4) || 4)
    };
    k.atob = k.decode = function (n, c) {
        n = n.replace(/[^A-Za-z0-9\+\/\=]/g, "");
        n = String(n).split("=");
        var m = n.length;
        do {
            --m;
            n[m] = a(n[m], true, j, l, 6, 8)
        } while (m > 0);
        n = n.join("");
        return k.raw === false || k.utf8decode || c ? d.decode(n) : n
    }
}(jQuery));
;
/*!
 * jQuery Cookie Plugin v1.4.0
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2013 Klaus Hartl
 * Released under the MIT license
 */
(function (a) {
    if (typeof define === "function" && define.amd) {
        define(["jquery"], a)
    } else {
        a(jQuery)
    }
}(function (f) {
    var a = /\+/g;

    function d(i) {
        return b.raw ? i : encodeURIComponent(i)
    }

    function g(i) {
        return b.raw ? i : decodeURIComponent(i)
    }

    function h(i) {
        return d(b.json ? JSON.stringify(i) : String(i))
    }

    function c(i) {
        if (i.indexOf('"') === 0) {
            i = i.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, "\\")
        }
        try {
            i = decodeURIComponent(i.replace(a, " "));
            return b.json ? JSON.parse(i) : i
        } catch (j) {
        }
    }

    function e(j, i) {
        var k = b.raw ? j : c(j);
        return f.isFunction(i) ? i(k) : k
    }

    var b = f.cookie = function (q, p, v) {
        if (p !== undefined && !f.isFunction(p)) {
            v = f.extend({}, b.defaults, v);
            if (typeof v.expires === "number") {
                var r = v.expires, u = v.expires = new Date();
                u.setTime(+u + r * 86400000)
            }
            return (document.cookie = [d(q), "=", h(p), v.expires ? "; expires=" + v.expires.toUTCString() : "", v.path ? "; path=" + v.path : "", v.domain ? "; domain=" + v.domain : "", v.secure ? "; secure" : ""].join(""))
        }
        var w = q ? undefined : {};
        var s = document.cookie ? document.cookie.split("; ") : [];
        for (var o = 0, m = s.length; o < m; o++) {
            var n = s[o].split("=");
            var j = g(n.shift());
            var k = n.join("=");
            if (q && q === j) {
                w = e(k, p);
                break
            }
            if (!q && (k = e(k)) !== undefined) {
                w[j] = k
            }
        }
        return w
    };
    b.defaults = {};
    f.removeCookie = function (j, i) {
        if (f.cookie(j) === undefined) {
            return false
        }
        f.cookie(j, "", f.extend({}, i, {expires: -1}));
        return !f.cookie(j)
    }
}));
;
jQuery.autocomplete = function (d, u) {
    var p = this;
    var y = $(d).attr("autocomplete", "off");
    if (u.inputClass) {
        y.addClass(u.inputClass)
    }
    var q = document.createElement("div");
    var h = $(q);
    h.hide().addClass(u.resultsClass).css("position", "absolute");
    if (u.width > 0) {
        h.css("width", u.width)
    }
    $("body").append(q);
    d.autocompleter = p;
    var H = null;
    var x = "";
    var I = -1;
    var j = {};
    var A = false;
    var l = false;
    var a = null;

    function m() {
        j = {};
        j.data = {};
        j.length = 0
    }

    m();
    if (u.data != null) {
        var s = "", N = {}, n = [];
        if (typeof u.url != "string") {
            u.cacheLength = 1
        }
        for (var L = 0; L < u.data.length; L++) {
            n = ((typeof u.data[L] == "string") ? [u.data[L]] : u.data[L]);
            if (n[0].length > 0) {
                s = n[0].substring(0, 1).toLowerCase();
                if (!N[s]) {
                    N[s] = []
                }
                N[s].push(n)
            }
        }
        for (var K in N) {
            u.cacheLength++;
            e(K, N[K])
        }
    }
    y.keydown(function (i) {
        a = i.keyCode;
        switch (i.keyCode) {
            case 38:
                i.preventDefault();
                D(-1);
                break;
            case 40:
                i.preventDefault();
                D(1);
                break;
            case 9:
            case 13:
                if (F()) {
                    y.get(0).blur();
                    i.preventDefault()
                }
                break;
            default:
                I = -1;
                if (H) {
                    clearTimeout(H)
                }
                H = setTimeout(function () {
                    t()
                }, u.delay);
                break
        }
    }).focus(function () {
        l = true
    }).blur(function () {
        l = false;
        c()
    });
    v();

    function t() {
        if (a == 46 || (a > 8 && a < 32)) {
            return h.hide()
        }
        var i = y.val();
        if (i == x) {
            return
        }
        x = i;
        if (i.length >= u.minChars) {
            y.addClass(u.loadingClass);
            C(i)
        } else {
            y.removeClass(u.loadingClass);
            h.hide()
        }
    }

    function D(k) {
        var i = $("li", q);
        if (!i) {
            return
        }
        I += k;
        if (I < 0) {
            I = 0
        } else {
            if (I >= i.size()) {
                I = i.size() - 1
            }
        }
        i.removeClass("ac_over");
        $(i[I]).addClass("ac_over")
    }

    function F() {
        var i = $("li.ac_over", q)[0];
        if (!i) {
            var k = $("li", q);
            if (u.selectOnly) {
                if (k.length == 1) {
                    i = k[0]
                }
            } else {
                if (u.selectFirst) {
                    i = k[0]
                }
            }
        }
        if (i) {
            r(i);
            return true
        } else {
            return false
        }
    }

    function r(i) {
        if (!i) {
            i = document.createElement("li");
            i.extra = [];
            i.selectValue = ""
        }
        var k = $.trim(i.selectValue ? i.selectValue : i.innerHTML);
        d.lastSelected = k;
        x = k;
        h.html("");
        y.val(k);
        v();
        if (u.onItemSelect) {
            setTimeout(function () {
                u.onItemSelect(i)
            }, 1)
        }
    }

    function b(P, k) {
        var O = y.get(0);
        if (O.createTextRange) {
            var i = O.createTextRange();
            i.collapse(true);
            i.moveStart("character", P);
            i.moveEnd("character", k);
            i.select()
        } else {
            if (O.setSelectionRange) {
                O.setSelectionRange(P, k)
            } else {
                if (O.selectionStart) {
                    O.selectionStart = P;
                    O.selectionEnd = k
                }
            }
        }
        O.focus()
    }

    function w(i) {
        if (a != 8) {
            y.val(y.val() + i.substring(x.length));
            b(x.length, i.length)
        }
    }

    function E() {
        var k = z(d);
        var i = (u.width > 0) ? u.width : y.width();
        h.css({width: parseInt(i) + "px", top: (k.y + d.offsetHeight) + "px", left: k.x + "px"}).show()
    }

    function c() {
        if (H) {
            clearTimeout(H)
        }
        H = setTimeout(v, 200)
    }

    function v() {
        if (H) {
            clearTimeout(H)
        }
        y.removeClass(u.loadingClass);
        if (h.is(":visible")) {
            h.hide()
        }
        if (u.mustMatch) {
            var i = y.val();
            if (i != d.lastSelected) {
                r(null)
            }
        }
    }

    function g(k, i) {
        if (i) {
            y.removeClass(u.loadingClass);
            q.innerHTML = "";
            if (!l || i.length == 0) {
                return v()
            }
            if ($.browser.msie) {
                h.append(document.createElement("iframe"))
            }
            q.appendChild(J(i));
            if (u.autoFill && (y.val().toLowerCase() == k.toLowerCase())) {
                w(i[0][0])
            }
            E()
        } else {
            v()
        }
    }

    function f(Q) {
        if (!Q) {
            return null
        }
        var k = [];
        var P = Q;
        for (var O = 0; O < P.length; O++) {
            var R = $.trim(P[O]);
            if (R) {
                k[k.length] = R.split(u.cellSeparator)
            }
        }
        return k
    }

    function J(T) {
        var S = document.createElement("ul");
        var Q = T.length;
        if ((u.maxItemsToShow > 0) && (u.maxItemsToShow < Q)) {
            Q = u.maxItemsToShow
        }
        for (var R = 0; R < Q; R++) {
            var U = T[R];
            if (!U) {
                continue
            }
            var O = document.createElement("li");
            if (u.formatItem) {
                O.innerHTML = u.formatItem(U, R, Q);
                O.selectValue = U[0]
            } else {
                O.innerHTML = U[0];
                O.selectValue = U[0]
            }
            var k = null;
            if (U.length > 1) {
                k = [];
                for (var P = 1; P < U.length; P++) {
                    k[k.length] = U[P]
                }
            }
            O.extra = k;
            S.appendChild(O);
            $(O).hover(function () {
                $("li", S).removeClass("ac_over");
                $(this).addClass("ac_over");
                I = $("li", S).indexOf($(this).get(0))
            }, function () {
                $(this).removeClass("ac_over")
            }).click(function (i) {
                i.preventDefault();
                i.stopPropagation();
                r(this)
            })
        }
        return S
    }

    function C(k) {
        if (!u.matchCase) {
            k = k.toLowerCase()
        }
        var i = u.cacheLength ? M(k) : null;
        if (i) {
            g(k, i)
        } else {
            if ((typeof u.url == "string") && (u.url.length > 0)) {
                $.get(o(k), function (O) {
                    O = f(O.list);
                    e(k, O);
                    g(k, O)
                })
            } else {
                y.removeClass(u.loadingClass)
            }
        }
    }

    function o(P) {
        var k = u.url + "?keyword=" + encodeURI(P);
        for (var O in u.extraParams) {
            k += "&" + O + "=" + encodeURI(u.extraParams[O])
        }
        return k
    }

    function M(T) {
        if (!T) {
            return null
        }
        if (j.data[T]) {
            return j.data[T]
        }
        if (u.matchSubset) {
            for (var R = T.length - 1; R >= u.minChars; R--) {
                var O = T.substr(0, R);
                var U = j.data[O];
                if (U) {
                    var S = [];
                    for (var P = 0; P < U.length; P++) {
                        var k = U[P];
                        var Q = k[0];
                        if (B(Q, T)) {
                            S[S.length] = k
                        }
                    }
                    return S
                }
            }
        }
        return null
    }

    function B(P, O) {
        if (!u.matchCase) {
            P = P.toLowerCase()
        }
        var k = P.indexOf(O);
        if (k == -1) {
            return false
        }
        return k == 0 || u.matchContains
    }

    this.flushCache = function () {
        m()
    };
    this.setExtraParams = function (i) {
        u.extraParams = i
    };
    this.findValue = function () {
        var k = y.val();
        if (!u.matchCase) {
            k = k.toLowerCase()
        }
        var i = u.cacheLength ? M(k) : null;
        if (i) {
            G(k, i)
        } else {
            if ((typeof u.url == "string") && (u.url.length > 0)) {
                $.get(o(k), function (O) {
                    O = f(O);
                    e(k, O);
                    G(k, O)
                })
            } else {
                G(k, null)
            }
        }
    };

    function G(T, S) {
        if (S) {
            y.removeClass(u.loadingClass)
        }
        var Q = (S) ? S.length : 0;
        var O = null;
        for (var R = 0; R < Q; R++) {
            var U = S[R];
            if (U[0].toLowerCase() == T.toLowerCase()) {
                O = document.createElement("li");
                if (u.formatItem) {
                    O.innerHTML = u.formatItem(U, R, Q);
                    O.selectValue = U[0]
                } else {
                    O.innerHTML = U[0];
                    O.selectValue = U[0]
                }
                var k = null;
                if (U.length > 1) {
                    k = [];
                    for (var P = 1; P < U.length; P++) {
                        k[k.length] = U[P]
                    }
                }
                O.extra = k
            }
        }
        if (u.onFindValue) {
            setTimeout(function () {
                u.onFindValue(O)
            }, 1)
        }
    }

    function e(k, i) {
        if (!i || !k || !u.cacheLength) {
            return
        }
        if (!j.length || j.length > u.cacheLength) {
            m();
            j.length++
        } else {
            if (!j[k]) {
                j.length++
            }
        }
        j.data[k] = i
    }

    function z(k) {
        var O = k.offsetLeft || 0;
        var i = k.offsetTop || 0;
        while (k = k.offsetParent) {
            O += k.offsetLeft;
            i += k.offsetTop
        }
        return {x: O, y: i}
    }
};
jQuery.fn.autocomplete = function (b, a, c) {
    a = a || {};
    a.url = b;
    a.data = ((typeof c == "object") && (c.constructor == Array)) ? c : null;
    a.inputClass = a.inputClass || "ac_input";
    a.resultsClass = a.resultsClass || "ac_results";
    a.lineSeparator = a.lineSeparator || "\n";
    a.cellSeparator = a.cellSeparator || "|";
    a.minChars = a.minChars || 1;
    a.delay = a.delay || 400;
    a.matchCase = a.matchCase || 0;
    a.matchSubset = a.matchSubset || 1;
    a.matchContains = a.matchContains || 0;
    a.cacheLength = a.cacheLength || 1;
    a.mustMatch = a.mustMatch || 0;
    a.extraParams = a.extraParams || {};
    a.loadingClass = a.loadingClass || "ac_loading";
    a.selectFirst = a.selectFirst || false;
    a.selectOnly = a.selectOnly || false;
    a.maxItemsToShow = a.maxItemsToShow || -1;
    a.autoFill = a.autoFill || false;
    a.width = parseInt(a.width, 10) || 0;
    this.each(function () {
        var d = this;
        new jQuery.autocomplete(d, a)
    });
    return this
};
jQuery.fn.autocompleteArray = function (b, a) {
    return this.autocomplete(null, a, b)
};
jQuery.fn.indexOf = function (b) {
    for (var a = 0; a < this.length; a++) {
        if (this[a] == b) {
            return a
        }
    }
    return -1
};
;
var static_base = "/static/nc/js/utils/";
(function ($) {
    var ie6 = ($.browser.msie && ($.browser.version == "6.0") && !$.support.style);
    $.utilBaseModal = {
        mask: function (option) {
            var obj = $(this);
            var options = $.extend({
                type: "box",
                title: "",
                level: 90,
                padding: 7,
                width: 500,
                height: 245,
                canclose: true,
                closecall: null,
                background: "#000000",
                opacity: 55
            }, option);
            var type = options.type;
            var level = parseInt(options.level);
            var width = parseInt(options.width);
            var title = options.title;
            var html = "";
            html += '<div id="' + type + '-modal-mask" class="modal-mask"></div>';
            html += '<div id="' + type + '-modal-outer" class="modal-outer">';
            html += '<div id="' + type + '-modal-inner" class="modal-inner">';
            html += '<div id="' + type + '-modal-title" class="modal-title">';
            html += '<a href="javascript:" id="' + type + '-modal-remove" class="modal-remove"><i class="model-close"></i></a>';
            if (options.title) {
                html += "<p>" + options.title + "</p>"
            }
            html += "</div>";
            html += '<div id="' + type + '-modal-content" class="modal-content"></div>';
            html += "</div>";
            html += "</div>";
            if (ie6) {
                $("body", "html").css({height: "100%", width: "100%"});
                if (document.getElementById(type + "-modal-hideselect") === null) {
                    $("body").append('<iframe id="' + type + '-modal-hideselect" class="modal-hideselect" allowTransparency="true" src="about:blank"></iframe>' + html)
                }
            } else {
                if (document.getElementById(type + "-modal-mask") === null) {
                    $("body").append(html)
                }
            }
            if (options.canclose) {
                $("#" + type + "-modal-remove").show()
            } else {
                $("#" + type + "-modal-remove").hide()
            }
            $("#" + type + "-modal-mask").css({
                "z-index": level,
                height: $(document).height(),
                "filter:": "alpha(opacity=" + options.opacity + ")",
                opacity: (options.opacity / 100),
                "-moz-opacity": (options.opacity / 100),
                background: options.background
            });
            $("#" + type + "-modal-outer").css({
                "z-index": level + 1,
                width: width + "px",
                padding: options.padding + "px"
            });
            $("#" + type + "-modal-remove").click(function () {
                if (options.closecall) {
                    eval(options.closecall + "()")
                } else {
                    $.utilBaseModal.remove(type)
                }
                return false
            })
        }, loading: function (type, status) {
            if (status == "hide") {
                $("#" + type + "-modal-loading").fadeOut(function () {
                    $(this).remove()
                })
            } else {
                $("#" + type + "-modal-mask").after('<div id="' + type + '-modal-loading" class="modal-loading"></div>');
                var obj = $("#" + type + "-modal-loading");
                var doc = document.documentElement;
                var width = self.innerWidth || (doc && doc.clientWidth) || document.body.clientWidth;
                var height = self.innerHeight || (doc && doc.clientHeight) || document.body.clientHeight;
                var scrollheight = parseInt($(document).scrollTop());
                var ewidth = obj.width();
                var eheight = obj.height();
                var eleft = (width - ewidth) / 2;
                var etop = (height - eheight) / 2 + (ie6 ? scrollheight : 0) - 20;
                obj.css({left: eleft + "px", top: etop + "px"})
            }
        }, position: function (type) {
            var width = $("body").width();
            var height = $(window).height();
            var scrollheight = parseInt($(document).scrollTop());
            var obj = $("#" + type + "-modal-outer");
            var ewidth = obj.width() + parseInt(obj.css("paddingLeft")) * 2;
            var eheight = obj.height() + parseInt(obj.css("paddingTop")) * 2;
            obj.data("w", width);
            obj.data("t", scrollheight);
            var eleft = (width - ewidth) / 2;
            var etop = (height - eheight) / 2 + (ie6 ? scrollheight : 0);
            if (height <= eheight) {
                etop = 5;
                $("body").data("scrollheight", scrollheight);
                $("body", "html").css({height: obj.height() + 10});
                $(document).scrollTop(0);
                obj.css("position", "absolute")
            }
            obj.css({left: eleft + "px", top: etop + "px"})
        }, positionUpdate: function (type) {
            var doc = document.documentElement;
            var width = self.innerWidth || (doc && doc.clientWidth) || document.body.clientWidth;
            var height = self.innerHeight || (doc && doc.clientHeight) || document.body.clientHeight;
            var scrollheight = parseInt($(document).scrollTop());
            var obj = $("#" + type + "-modal-outer");
            var w = parseInt(obj.data("w"));
            $("#" + type + "-modal-outer").data("w", width);
            var eleft = parseInt(obj.css("left"));
            var eheight = obj.height() + parseInt(obj.css("paddingTop")) * 2;
            var etop = (height - eheight) / 2 + (ie6 ? scrollheight : 0);
            w != width && obj.css("left", (eleft - parseInt((w - width) / 2)) + "px");
            if (height <= eheight) {
                etop = 5;
                $("body").data("scrollheight", scrollheight);
                $("body", "html").css({height: obj.height() + 10});
                $(document).scrollTop(0);
                obj.css("position", "absolute")
            }
            obj.css({left: eleft + "px", top: etop + "px"})
        }, remove: function (type) {
            $("#" + type + "-modal-close").unbind("click");
            $("#" + type + "-modal-mask,#" + type + "-modal-hideselect,#" + type + "-modal-outer,#" + type + "-modal-innter").trigger("unload").unbind().remove();
            if ($("body").data("scrollheight") > 0) {
                $(document).scrollTop($("body").data("scrollheight"));
                $("body").removeData("scrollheight")
            }
            return false
        }
    }, $.utilAlertModal = {
        draggable: true, ok_button: "确 定", cancel_button: "取 消", alert: function (message, title, callback) {
            if (title == null) {
                title = "系统提示"
            }
            $.utilAlertModal.show(title, message, null, "alert", function (result) {
                if (callback) {
                    callback(result)
                }
            });
            return false
        }, popwin: function (message, title, callback) {
            if (title == null) {
                title = "提示"
            }
            $.utilAlertModal.show(title, message, null, "popwin", function (result) {
                if (callback) {
                    callback(result)
                }
            })
        }, confirm: function (message, title, callback) {
            if (title == null) {
                title = "确认"
            }
            $.utilAlertModal.show(title, message, null, "confirm", function (result) {
                if (callback) {
                    callback(result)
                }
            });
            return false
        }, prompt: function (message, value, title, callback) {
            if (title == null) {
                title = "Prompt"
            }
            $.utilAlertModal.show(title, message, value, "prompt", function (result) {
                if (callback) {
                    callback(result)
                }
            });
            return false
        }, show: function (title, msg, value, mtype, callback) {
            var type = "alert";
            $.utilBaseModal.mask({
                type: type,
                title: title,
                level: 100,
                padding: 1,
                width: 340,
                height: 150,
                canclose: true,
                background: "#FFFFFF",
                opacity: 10
            });
            var html = "";
            html += '<div id="alert-content"><em></em><div>' + msg + "</div></div>";
            html += '<div id="alert-bar"></div>';
            $("#" + type + "-modal-content").html(html);
            $.utilBaseModal.position(type);
            switch (mtype) {
                case"alert":
                    $("#alert-content>em").attr("class", "fa fa-exclamation-circle");
                    $("#alert-bar").html('<button type="button" id="alert-ok" class="button button-s button-red">' + $.utilAlertModal.ok_button + "</button>");
                    $("#alert-ok").focus();
                    $("#alert-ok,#popup_close").click(function () {
                        $.utilBaseModal.remove(type);
                        callback(true)
                    });
                    $("#alert-ok").keypress(function (e) {
                        if (e.keyCode == 13 || e.keyCode == 27) {
                            $("#alert-ok").trigger("click")
                        }
                    });
                    break;
                case"popwin":
                    $("#alert-bar").html('<button type="button" id="alert-ok" class="button button-s button-green">' + $.utilAlertModal.ok_button + "</button>");
                    $("#alert-ok").focus();
                    $("#alert-ok").click(function () {
                        $.utilBaseModal.remove(type);
                        callback(true)
                    });
                    $("#alert-ok").keypress(function (e) {
                        if (e.keyCode == 13 || e.keyCode == 27) {
                            $("#alert-ok").trigger("click")
                        }
                    });
                    setTimeout("$.utilAlertModal._hide()", 1500);
                    break;
                case"confirm":
                    $("#alert-content>em").attr("class", "fa fa-question-circle");
                    $("#" + type + "-modal-outer").width(375);
                    $("#alert-bar").html('<button type="button" id="alert-ok" class="button button-s button-green">' + $.utilAlertModal.ok_button + '</button> <button type="button" id="alert-cancel" class="button button-s">' + $.utilAlertModal.cancel_button + "</button>");
                    $("#alert-ok").focus();
                    $("#alert-ok").click(function () {
                        $.utilBaseModal.remove(type);
                        if (callback) {
                            callback(true)
                        }
                    });
                    $("#alert-cancel").click(function () {
                        $.utilBaseModal.remove(type);
                        if (callback) {
                            callback(false)
                        }
                    });
                    $("#alert-ok, #alert-cancel").keypress(function (e) {
                        if (e.keyCode == 13) {
                            $("#alert-ok").trigger("click")
                        }
                        if (e.keyCode == 27) {
                            $("#alert-cancel").trigger("click")
                        }
                    });
                    break
            }
            if (ie6) {
                var doc = document.documentElement;
                var height = self.innerHeight || (doc && doc.clientHeight) || document.body.clientHeight;
                var eheight = $("#" + type + "-modal-outer").height() + parseInt($("#" + type + "-modal-outer").css("paddingTop")) * 2;
                height >= eheight && $(window).scroll(function () {
                    $.utilBaseModal.positionUpdate(type)
                })
            }
            $(window).resize(function () {
                $.utilBaseModal.positionUpdate(type)
            });
            if ($.utilAlertModal.draggable) {
                $.getScript("/static/nc/js/utils/jquery.ui.js", function () {
                    try {
                        $("#" + type + "-modal-outer").draggable({handle: $("#" + type + "-modal-title")});
                        $("#" + type + "-modal-title").css({cursor: "move"})
                    } catch (e) {
                    }
                })
            }
            return false
        }
    }, jAlert = function (message, title, callback) {
        $.utilAlertModal.alert(message, title, callback)
    }, jPopwin = function (message, title, callback) {
        $.utilAlertModal.popwin(message, title, callback)
    }, jConfirm = function (message, title, callback) {
        $.utilAlertModal.confirm(message, title, callback)
    }, $.popModal = function (option) {
        var type = "pop";
        var options = $.extend({
            iframe: false,
            url: null,
            content: null,
            title: "",
            padding: 0,
            width: 560,
            height: 245,
            datatype: "JSON",
            canclose: true,
            closecall: null,
            background: "#000000",
            opacity: 30,
            scroll: "yes"
        }, option);
        $.utilBaseModal.mask({
            type: type,
            title: options.title,
            padding: options.padding,
            width: options.width,
            height: options.height,
            canclose: options.canclose,
            closecall: options.closecall,
            background: options.background,
            opacity: options.opacity
        });
        $.utilBaseModal.loading(type, "show");
        if (options.iframe == true) {
            $("#" + type + "-modal-content").html('<iframe id="' + type + '-modal-frame" name="' + type + "-modal-iframe" + Math.round(Math.random() * 1000) + '" width="' + options.width + ' height="' + (options.height - 30) + '" frameborder="0" hspace="0" src="' + options.url + '"></iframe>');
            $.utilBaseModal.loading(type, "hide");
            $.utilBaseModal.position(type)
        } else {
            $("#" + type + "-modal-outer").hide();
            if (options.url) {
                if (options.datatype == "JSON") {
                    $.getJSON(options.url + "&callback=?", function (data) {
                        $.utilBaseModal.loading(type, "hide");
                        $("#" + type + "-modal-outer").show();
                        $("#" + type + "-modal-content").html(data);
                        $.utilBaseModal.position(type);
                        ispos = false
                    })
                } else {
                    $.get(options.url, function (data) {
                        $.utilBaseModal.loading(type, "hide");
                        $("#" + type + "-modal-outer").show();
                        $("#" + type + "-modal-content").html(data);
                        $.utilBaseModal.position(type);
                        ispos = false
                    })
                }
            }
            if (options.content) {
                $("#" + type + "-modal-outer").show();
                $("#" + type + "-modal-content").html(options.content);
                $.utilBaseModal.loading(type, "hide");
                $.utilBaseModal.position(type)
            }
        }
        if (ie6) {
            var doc = document.documentElement;
            var height = self.innerHeight || (doc && doc.clientHeight) || document.body.clientHeight;
            var eheight = $("#" + type + "-modal-outer").height() + parseInt($("#" + type + "-modal-outer").css("paddingTop")) * 2;
            height >= eheight && $(window).scroll(function () {
                $.utilBaseModal.positionUpdate(type)
            })
        }
        $(window).resize(function () {
            $.utilBaseModal.positionUpdate(type)
        })
    }, $.popModalClose = function () {
        $.utilBaseModal.remove("pop")
    }, $.popModalUpdate = function () {
        $.utilBaseModal.positionUpdate("pop")
    }, $.fn.floatModal = function (option) {
        var options = $.extend({url: null, str: null, title: null, direction: "right"}, option);
        var e = $(this), id = "float-modal-" + e.attr("id"), offset = e.offset(), t = null;
        e.parent().css("position", "relative");
        $(".float-modal").remove();
        if ($("#" + id).size() > 0) {
            $("#" + id).show()
        } else {
            var str = '<div id="' + id + '" class="float-modal">';
            str += '<h3><a href="javascript:" class="close"><i class="fa fa-close"></i></a><span>' + options.title + "</span></h3>";
            str += '<div class="modal-cont"></div>';
            str += "</div>";
            e.after(str);
            if (options.direction == "left") {
                $("#" + id).css({left: 0})
            } else {
                $("#" + id).css({right: 0})
            }
        }
        if (options.url) {
            $("#" + id + " .modal-cont").html('<div style="width:150px;line-height:50px;text-align:center;">正在加载...请稍后</div>');
            $.get(options.url, function (data) {
                $("#" + id + " .modal-cont").html(data)
            })
        } else {
            $("#" + id + " .modal-cont").html(options.str)
        }
        $(".float-modal a.close").click(function () {
            $(this).parent().parent().remove();
            return false
        })
    }, $.flashTip = function (str, type) {
        var type_style = "";
        if (type == null || type == "") {
            type = "success"
        }
        if (type == "success") {
            type_style = "check-circle"
        }
        if (type == "warning") {
            type_style = "exclamation-circle"
        }
        if (type == "error") {
            type_style = "ban"
        }
        if ($("#flash-tips").size() > 0) {
            $("#flash-tips").show()
        } else {
            $("body").append('<div id="flash-tips" class="flash-tips-' + type + '"><i class="fa fa-' + type_style + '"></i>' + str + "</div>")
        }
        var obj = $("#flash-tips"), doc = document.documentElement,
            width = self.innerWidth || (doc && doc.clientWidth) || document.body.clientWidth,
            height = self.innerHeight || (doc && doc.clientHeight) || document.body.clientHeight,
            scrollheight = parseInt($(document).scrollTop()),
            ewidth = obj.width() + parseInt(obj.css("paddingLeft")) + parseInt(obj.css("paddingRight")) + parseInt(obj.css("borderLeftWidth")) * 2,
            eheight = obj.height(), eleft = (width - ewidth) / 2,
            etop = (height - eheight) / 2 + (ie6 ? scrollheight : 0) - 20;
        obj.css({left: eleft + "px", top: etop + "px"});
        obj.click(function () {
            obj.fadeOut(function () {
                $(this).remove()
            })
        });
        setTimeout(function () {
            obj.fadeOut(function () {
                $(this).remove()
            })
        }, "3500")
    }, $.fn.lazyload = function (setting) {
        var options = $.extend({height: 0}, setting);
        var height = options.height;
        var obj = $(this);
        var pageTop = function () {
            var d = document,
                y = (navigator.userAgent.toLowerCase().match(/iPad/i) == "ipad") ? window.pageYOffset : Math.max(d.documentElement.scrollTop, d.body.scrollTop);
            return d.documentElement.clientHeight + y - options.height
        };
        var objLoad = function () {
            obj.each(function () {
                if ($(this).offset().top <= pageTop()) {
                    var this_obj = $(this);
                    var src = this_obj.attr("data-image");
                    var url = this_obj.attr("data-url");
                    src && this_obj.attr("src", src).removeAttr("data-image");
                    if (url) {
                        this_obj.removeAttr("data-url");
                        $.get(url, function (data) {
                            this_obj.html(data)
                        })
                    }
                }
            })
        };
        objLoad();
        $(window).bind("scroll", function () {
            objLoad()
        })
    }, $.fn.utilSimpleDate = function (option) {
        var options = $.extend({date: null, split: "/"}, option);
        var e = $(this);
        var nd;
        var d = new Date();
        var all_year = d.getFullYear() - 14;
        var this_year;
        var this_month;
        var this_day;
        if (options.date) {
            nd = options.date.split(options.split)
        } else {
            nd = e.attr("rel").split(options.split)
        }
        this_year = nd[0];
        this_month = nd[1];
        this_day = nd[2];
        var e_year = e.find("select[name=year]");
        var e_month = e.find("select[name=month]");
        var e_day = e.find("select[name=day]");
        var i = 0;
        var selected = "";
        e_year.append('<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>');
        for (i = all_year; i > all_year - 56; i--) {
            selected = this_year == i ? " selected" : "";
            e_year.append('<option value="' + i + '"' + selected + ">&nbsp;" + i + "&nbsp;</option>")
        }
        e_month.append('<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>');
        for (i = 1; i <= 12; i++) {
            selected = this_month == i ? " selected" : "";
            e_month.append('<option value="' + i + '"' + selected + ">&nbsp;" + i + "&nbsp;</option>")
        }
        e_year.change(function () {
            build_days(e_day.val())
        });
        e_month.change(function () {
            build_days(e_day.val())
        });
        if (this_day != null) {
            build_days(this_day)
        }

        function build_days(this_day) {
            var end_day = 31;
            var year = parseInt(e_year.val());
            var month = parseInt(e_month.val());
            if (month < 8 && month % 2 == 0) {
                end_day = 30;
                if (month == 2) {
                    end_day = 28;
                    if (year != 0 && year % 4 == 0) {
                        end_day = 29
                    }
                }
            } else {
                if (month >= 8 && month % 2 != 0) {
                    end_day = 30
                }
            }
            if (month) {
                e_day.html('<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>');
                for (i = 1; i <= end_day; i++) {
                    selected = this_day == i ? " selected" : "";
                    if (i < 10 && i > 0) {
                        i = "0" + i
                    }
                    e_day.append('<option value="' + i + '"' + selected + ">&nbsp;" + i + "&nbsp;</option>")
                }
            }
        }
    }, $.fn.utilSetArea = function () {
        var province_json = static_base + "/json-array-of-province.js";
        var city_json = static_base + "/json-array-of-city.js";
        var district_json = static_base + "/json-array-of-district.js";
        var default_province = '<option value="">选择省份</option>';
        var default_city = '<option value="">选择城市</option>';
        var default_district = '<option value="">选择区县</option>';
        var province = new Array();
        var city = new Array();
        var district = new Array();
        var e = $(this);
        var array;
        var type = e.find(".province").size() > 0 ? 1 : 0;
        if (province.length == 0 || city.length == 0 || district.length == 0) {
            $.when($.getJSON(province_json, function (data) {
                $.each(data, function (i, d) {
                    province[d.code] = d.name
                })
            }), $.getJSON(city_json, function (data) {
                $.each(data, function (i, d) {
                    city[d.code] = d.name
                })
            }), $.getJSON(district_json, function (data) {
                $.each(data, function (i, d) {
                    district[d.code] = d.name
                })
            })).then(function () {
                if (type == 0) {
                    $.each(e, function (i, d) {
                        array = $(d).attr("rel").split(",");
                        $(d).html(province[array[0]] + city[array[1]] + district[array[2]])
                    })
                } else {
                    var obj_p = e.find(".province");
                    var obj_c = e.find(".city");
                    var obj_d = e.find(".district");
                    build_list(province, obj_p.attr("rel"), obj_p, null, 0, default_province);
                    build_list(city, obj_c.attr("rel"), obj_c, obj_p, 2, default_city);
                    build_list(district, obj_d.attr("rel"), obj_d, obj_c, 4, default_district);
                    obj_p.change(function () {
                        build_list(city, obj_c.attr("rel"), obj_c, obj_p, 2, default_city);
                        build_list(district, obj_d.attr("rel"), obj_d, obj_c, 4, default_district)
                    });
                    obj_c.change(function () {
                        build_list(district, obj_d.attr("rel"), obj_d, obj_c, 4, default_district)
                    })
                }
            })
        }

        function build_list(data, value, obj, pre_obj, strlen, def) {
            var selected;
            var option = "";
            for (var i in data) {
                selected = "";
                if (strlen === 0) {
                    if (value !== "" && value !== 0 && i === value) {
                        selected = " selected";
                        value = ""
                    }
                    option += '<option value="' + i + '"' + selected + ">" + data[i] + "</option>"
                } else {
                    var p = pre_obj.val();
                    if (p.substring(0, strlen) === i.substring(0, strlen)) {
                        if (value !== "" && value !== 0 && i === value) {
                            selected = " selected";
                            value = ""
                        }
                        option += '<option value="' + i + '"' + selected + ">" + data[i] + "</option>"
                    }
                }
            }
            obj.html(def + option)
        }
    }, $.fn.utilSetNumber = function () {
        $(this).css("ime-mode", "disabled");
        this.bind("keypress", function (e) {
            var code = (e.keyCode ? e.keyCode : e.which);
            if (!$.browser.msie && (e.keyCode == 8)) {
                return
            }
            return code >= 48 && code <= 57 || code == 46
        });
        this.bind("blur", function () {
            if (this.value.lastIndexOf(".") == (this.value.length - 1)) {
                this.value = this.value.substr(0, this.value.length - 1)
            } else {
                if (isNaN(this.value)) {
                    this.value = ""
                }
            }
        });
        this.bind("paste", function () {
            var s = clipboardData.getData("text");
            if (!/\D/.test(s)) {
            }
            value = s.replace(/\D/, "");
            return false
        });
        this.bind("dragenter", function () {
            return false
        });
        this.bind("keyup", function () {
            this.value = this.value.replace(/[^\d.]/g, "");
            this.value = this.value.replace(/^\./g, "");
            this.value = this.value.replace(/\.{2,}/g, ".");
            this.value = this.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".")
        })
    }, $.fn.utilValidateForm = function () {
        var obj_form = $(this);
        var errors = 0;
        if (obj_form.find(".tip-validate").size() > 0) {
            $.each(obj_form.find(".tip-validate"), function (i, o) {
                var error = 0, message, target_array = [], target_type = 0;
                var obj_target = $(o).attr("data-target"), validate_rule = $(o).attr("data-rule"),
                    min_length = $(o).attr("min-length"), max_length = $(o).attr("max-length"),
                    compare_with = $(o).attr("compare-with"), validate_function = $(o).attr("data-function");
                if (obj_target.indexOf("|") != -1) {
                    target_array = obj_target.split("|");
                    target_type = 1
                } else {
                    if (obj_target.indexOf("&") != -1) {
                        target_array = obj_target.split("&");
                        target_type = 2
                    } else {
                        target_array.push(obj_target);
                        target_type = 0
                    }
                }
                if (min_length != "" && min_length != undefined) {
                    min_length = parseInt(min_length)
                } else {
                    min_length = 0
                }
                if (max_length != "" && max_length != undefined) {
                    max_length = parseInt(max_length)
                } else {
                    max_length = 0
                }
                if (validate_rule == undefined && validate_function == undefined) {
                    validate_rule = "empty"
                }
                if (validate_rule == "empty") {
                    $.each(target_array, function (ii, oo) {
                        if ($("#" + oo).size() > 0 && $("#" + oo).val() == "") {
                            error++
                        }
                    });
                    message = "该项不能为空，请正确输入"
                }
                if (validate_rule == "mobile") {
                    $.each(target_array, function (ii, oo) {
                        if ($("#" + oo).size() > 0 && !$("#" + oo).utilValidateMobile()) {
                            error++
                        }
                    });
                    message = "格式不正确，请输入正确手机号码"
                }
                if (min_length > 0) {
                    $.each(target_array, function (ii, oo) {
                        if ($("#" + oo) != "" && $("#" + oo).val().length < min_length) {
                            error++
                        }
                    });
                    message = "长度不能小于" + min_length + "位"
                }
                if (max_length > 0) {
                    $.each(target_array, function (ii, oo) {
                        if ($("#" + oo) != "" && $("#" + oo).val().length > max_length) {
                            error++
                        }
                    });
                    message = "长度不能大于" + max_length + "位"
                }
                if (validate_rule == "nickname") {
                    var a = parseInt($(o).attr("byte-max"));
                    var b = parseInt($(o).attr("byte-min"));
                    var bytemax = a ? a : 30, bytemin = b ? b : 4;
                    $.each(target_array, function (ii, oo) {
                        console.log(target_array);
                        if (($("#" + oo).size() > 0 && !$("#" + oo).utilValidateName()) || $.utilValidateByteLength($("#" + oo).val()) < bytemin || $.utilValidateByteLength($("#" + oo).val()) > bytemax) {
                            error++
                        }
                    });
                    message = "含有特殊符号或者昵称长度不符合要求"
                }
                if (validate_rule == "name") {
                    $.each(target_array, function (ii, oo) {
                        if (($("#" + oo).size() > 0 && !$("#" + oo).utilValidateName()) || $("#" + oo).val().length < 2) {
                            error++
                        }
                    });
                    message = "含有特殊符号或者名称长度不符合要求"
                }
                if (validate_rule == "email") {
                    $.each(target_array, function (ii, oo) {
                        if ($("#" + oo).size() > 0 && !$("#" + oo).utilValidateEmail()) {
                            error++
                        }
                    });
                    message = "格式不正确，请输入正确邮箱地址"
                }
                if (validate_rule == "byte") {
                    var a = parseInt($(o).attr("byte-max"));
                    var b = parseInt($(o).attr("byte-min"));
                    var bytemax = a ? a : 30, bytemin = b ? b : 4;
                    $.each(target_array, function (ii, oo) {
                        if ($.utilValidateByteLength($("#" + oo).val()) < bytemin || $.utilValidateByteLength($("#" + oo).val()) > bytemax) {
                            error++
                        }
                    });
                    message = "长度必须在" + bytemin + "字符到" + bytemax + "字符之间"
                }
                if (validate_rule == "sixDigit") {
                    $.each(target_array, function (ii, oo) {
                        if ($("#" + oo).size() > 0 && ($("#" + oo).val().length < 8 || $("#" + oo).val().length > 20)) {
                            error++
                        }
                    });
                    message = "密码必须大于8位小于20位"
                }
                if (compare_with) {
                    if ($("#" + obj_target).val() != $("#" + compare_with).val()) {
                        error++;
                        message = "两次输入的值不一致"
                    }
                }
                if (validate_function) {
                    try {
                        if (typeof (eval(validate_function)) == "function") {
                            if (eval(validate_function + "()") != true) {
                                error++;
                                message = eval(validate_function + "()")
                            }
                        }
                    } catch (e) {
                        alert("不存在" + validate_function + "这个函数");
                        return false
                    }
                }
                if (error > 0) {
                    errors++;
                    $(o).show().html(message)
                } else {
                    $(o).hide()
                }
            });
            if (errors > 0) {
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }, $.fn.utilValidateMobile = function () {
        $(this).css("ime-mode", "disabled");
        var re_mobile = /^[0-9]*$/;
        if (re_mobile.test($(this).val()) && $(this).val().length > 4) {
            return true
        } else {
            return false
        }
    }, $.fn.utilValidateName = function () {
        $(this).css("ime-mode", "disabled");
        var re_name = /^[\u4E00-\u9FA5A-Za-z0-9_]+$/;
        if (re_name.test($(this).val())) {
            return true
        } else {
            return false
        }
    }, $.utilValidateByteLength = function (val) {
        var Zhlength = 0;
        var Enlength = 0;
        for (var i = 0; i < val.length; i++) {
            if (val.substring(i, i + 1).match(/[^\x00-\xff]/ig) != null) {
                Zhlength += 1
            } else {
                Enlength += 1
            }
        }
        return (Zhlength * 2) + Enlength
    }, $.fn.utilValidateEmail = function () {
        $(this).css("ime-mode", "disabled");
        var re_email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
        if (re_email.test($(this).val())) {
            return true
        } else {
            return false
        }
    }, $.utilFormatCurrency = function (num) {
        if (!isNaN(num)) {
            num = num.toString().replace(/\$|\,/g, "");
            if (isNaN(num)) {
                num = "0"
            }
            sign = (num == (num = Math.abs(num)));
            num = Math.floor(num * 100 + 0.50000000001);
            cents = num % 100;
            num = Math.floor(num / 100).toString();
            if (cents < 10) {
                cents = "0" + cents
            }
            for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++) {
                num = num.substring(0, num.length - (4 * i + 3)) + "," + num.substring(num.length - (4 * i + 3))
            }
            return (((sign) ? "" : "-") + num + "." + cents)
        }
    }, $.fn.utilTransAmountToCN = function (num) {
        for (i = num.length - 1; i >= 0; i--) {
            num = num.replace(",", "");
            num = num.replace(" ", "")
        }
        num = num.replace("￥", "");
        if (isNaN(num)) {
            alert("请检查小写金额是否正确");
            return
        }
        part = String(num).split(".");
        newchar = "";
        for (i = part[0].length - 1; i >= 0; i--) {
            if (part[0].length > 10) {
                alert("位数过大，无法计算");
                return ""
            }
            tmpnewchar = "";
            perchar = part[0].charAt(i);
            switch (perchar) {
                case"0":
                    tmpnewchar = "零" + tmpnewchar;
                    break;
                case"1":
                    tmpnewchar = "壹" + tmpnewchar;
                    break;
                case"2":
                    tmpnewchar = "贰" + tmpnewchar;
                    break;
                case"3":
                    tmpnewchar = "叁" + tmpnewchar;
                    break;
                case"4":
                    tmpnewchar = "肆" + tmpnewchar;
                    break;
                case"5":
                    tmpnewchar = "伍" + tmpnewchar;
                    break;
                case"6":
                    tmpnewchar = "陆" + tmpnewchar;
                    break;
                case"7":
                    tmpnewchar = "柒" + tmpnewchar;
                    break;
                case"8":
                    tmpnewchar = "捌" + tmpnewchar;
                    break;
                case"9":
                    tmpnewchar = "玖" + tmpnewchar;
                    break
            }
            switch (part[0].length - i - 1) {
                case 0:
                    tmpnewchar = tmpnewchar + "元";
                    break;
                case 1:
                    if (perchar != 0) {
                        tmpnewchar = tmpnewchar + "拾"
                    }
                    break;
                case 2:
                    if (perchar != 0) {
                        tmpnewchar = tmpnewchar + "佰"
                    }
                    break;
                case 3:
                    if (perchar != 0) {
                        tmpnewchar = tmpnewchar + "仟"
                    }
                    break;
                case 4:
                    tmpnewchar = tmpnewchar + "万";
                    break;
                case 5:
                    if (perchar != 0) {
                        tmpnewchar = tmpnewchar + "拾"
                    }
                    break;
                case 6:
                    if (perchar != 0) {
                        tmpnewchar = tmpnewchar + "佰"
                    }
                    break;
                case 7:
                    if (perchar != 0) {
                        tmpnewchar = tmpnewchar + "仟"
                    }
                    break;
                case 8:
                    tmpnewchar = tmpnewchar + "亿";
                    break;
                case 9:
                    tmpnewchar = tmpnewchar + "拾";
                    break
            }
            newchar = tmpnewchar + newchar
        }
        if (num.indexOf(".") != -1) {
            if (part[1].length > 2) {
                part[1] = part[1].substr(0, 2)
            }
            for (i = 0; i < part[1].length; i++) {
                tmpnewchar = "";
                perchar = part[1].charAt(i);
                switch (perchar) {
                    case"0":
                        tmpnewchar = "零" + tmpnewchar;
                        break;
                    case"1":
                        tmpnewchar = "壹" + tmpnewchar;
                        break;
                    case"2":
                        tmpnewchar = "贰" + tmpnewchar;
                        break;
                    case"3":
                        tmpnewchar = "叁" + tmpnewchar;
                        break;
                    case"4":
                        tmpnewchar = "肆" + tmpnewchar;
                        break;
                    case"5":
                        tmpnewchar = "伍" + tmpnewchar;
                        break;
                    case"6":
                        tmpnewchar = "陆" + tmpnewchar;
                        break;
                    case"7":
                        tmpnewchar = "柒" + tmpnewchar;
                        break;
                    case"8":
                        tmpnewchar = "捌" + tmpnewchar;
                        break;
                    case"9":
                        tmpnewchar = "玖" + tmpnewchar;
                        break
                }
                if (i == 0) {
                    tmpnewchar = tmpnewchar + "角"
                }
                if (i == 1) {
                    tmpnewchar = tmpnewchar + "分"
                }
                newchar = newchar + tmpnewchar
            }
        }
        while (newchar.search("零零") != -1) {
            newchar = newchar.replace("零零零", "");
            newchar = newchar.replace("零零", "零");
            newchar = newchar.replace("零亿", "亿");
            newchar = newchar.replace("亿万", "亿");
            newchar = newchar.replace("零万", "万");
            newchar = newchar.replace("零元", "元");
            newchar = newchar.replace("零角", "");
            newchar = newchar.replace("零分", "");
            if (newchar.charAt(newchar.length - 1) == "元" || newchar.charAt(newchar.length - 1) == "角") {
                newchar = newchar + "整"
            }
        }
        newchar = newchar.replace("零元", "元");
        return newchar
    }, $.fn.countdown = function () {
        var obj = $(this);
        var type = obj.attr("data-type");
        var defined = obj.attr("data-defined");
        var func = obj.attr("data-function");
        var startTime = (new Date(obj.attr("data-startTime"))).getTime();
        var endTime = (new Date(obj.attr("data-endTime"))).getTime();
        var nowTime = (new Date(_server_time)).getTime();
        var st = startTime - nowTime, et = endTime - nowTime;
        var timestr;
        if (type == undefined) {
            type = (st > 0) ? "tostart" : "toend"
        }
        var decrease = 1000;
        if (defined == "millisecond") {
            decrease = 100
        }
        timestr = (st > 0) ? st : et;
        output();
        var timer = setInterval(function () {
            output();
            if (timestr < 0) {
                clearInterval(timer)
            }
            timestr -= decrease
        }, decrease);

        function output() {
            var countdown_template, time_tip;
            if (type == "toend") {
                countdown_template = '<span class="normal">还剩~timestamp~结束</span>';
                time_tip = "已经结束"
            } else {
                if (type == "tostart") {
                    countdown_template = '<span class="normal">还有~timestamp~开始</span>';
                    time_tip = "已经开始"
                }
            }
            var time = expireTime(timestr);
            if (timestr <= 0) {
                if (typeof (eval(func)) == "function") {
                    obj.html(eval(func))
                } else {
                    obj.html(time_tip)
                }
            } else {
                if (type == "split-time") {
                    obj.find(".day").html(time.day);
                    obj.find(".hour").html(time.hour);
                    obj.find(".minute").html(time.minute);
                    obj.find(".second").html(time.second);
                    obj.find(".millisecond").html(time.millisecond)
                } else {
                    var timestamp = "";
                    if (time.day > 0) {
                        timestamp += "<strong>" + time.day + "</strong>天"
                    }
                    if (time.hour > 0) {
                        timestamp += "<strong>" + time.hour + "</strong>小时"
                    }
                    timestamp += "<strong>" + time.minute + "</strong>分<strong>" + time.second + "</strong>秒";
                    if (defined == "millisecond") {
                        timestamp += "<strong>" + time.millisecond + "</strong>"
                    }
                    var str = countdown_template.replace("~timestamp~", timestamp);
                    obj.html(str)
                }
            }
        }

        function expireTime(time) {
            var array = new Array();
            if (time > 0) {
                var tDay = Math.floor(time / 86400000);
                tHour = Math.floor((time / 3600000) % 24);
                if (tHour < 10) {
                    tHour = "0" + tHour
                }
                tMinute = Math.floor((time / 60000) % 60);
                if (tMinute < 10) {
                    tMinute = "0" + tMinute
                }
                tSecond = Math.floor((time / 1000) % 60);
                if (tSecond < 10) {
                    tSecond = "0" + tSecond
                }
                tMillisecond = Math.floor((time / 100) % 10);
                array.day = tDay;
                array.hour = tHour;
                array.minute = tMinute;
                array.second = tSecond;
                array.millisecond = tMillisecond
            } else {
                array.day = "00";
                array.hour = "00";
                array.minute = "00";
                array.second = "00";
                array.millisecond = "0"
            }
            return array
        }
    }, $.fn.utilRating = function () {
        $.each($(this), function (x, y) {
            var obj = $(y);
            var num = obj.find(".star").size();
            var input_num = parseInt(obj.find("input").val());
            if (input_num == NaN) {
                input_num = 0
            }
            if (input_num > 0) {
                for (var n = 4; n >= (5 - input_num); n--) {
                    obj.find(".star:eq(" + n + ")").addClass("checked")
                }
            }
            obj.find(".star").click(function () {
                var i = $(this).index();
                var obj_p = $(this).parent();
                $(this).siblings("input").val(5 - i);
                obj_p.find(".star").removeClass("checked");
                for (var m = 4; m >= i; m--) {
                    obj_p.find(".star:eq(" + m + ")").addClass("checked")
                }
            })
        })
    }, $.fn.serializeObject = function () {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]]
                }
                o[this.name].push(this.value || "")
            } else {
                o[this.name] = this.value || ""
            }
        });
        return o
    }, $.unixtime = function (param) {
        var timestamp = param.replace(/^\s+|\s+$/, "");
        if (/^\d{10}$/.test(timestamp)) {
            timestamp *= 1000
        } else {
            if (/^\d{13}$/.test(timestamp)) {
                timestamp = parseInt(timestamp)
            } else {
                alert("时间戳格式不正确！");
                return
            }
        }

        function format(timestamp) {
            var time = new Date(timestamp);
            var year = time.getFullYear();
            var month = (time.getMonth() + 1) > 9 && (time.getMonth() + 1) || ("0" + (time.getMonth() + 1));
            var date = time.getDate() > 9 && time.getDate() || ("0" + time.getDate());
            var hour = time.getHours() > 9 && time.getHours() || ("0" + time.getHours());
            var minute = time.getMinutes() > 9 && time.getMinutes() || ("0" + time.getMinutes());
            var second = time.getSeconds() > 9 && time.getSeconds() || ("0" + time.getSeconds());
            var YmdHis = year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
            return YmdHis
        }

        var YmdHis = format(timestamp);
        return YmdHis
    }
})(jQuery);
Date.prototype.format = function (b) {
    var c = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        S: this.getMilliseconds()
    };
    if (/(y+)/.test(b)) {
        b = b.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length))
    }
    for (var a in c) {
        if (new RegExp("(" + a + ")").test(b)) {
            b = b.replace(RegExp.$1, RegExp.$1.length == 1 ? c[a] : ("00" + c[a]).substr(("" + c[a]).length))
        }
    }
    return b
};
;
var browser = {
    versions: function () {
        var a = navigator.userAgent, b = navigator.appVersion;
        return {
            trident: a.indexOf("Trident") > -1,
            presto: a.indexOf("Presto") > -1,
            webKit: a.indexOf("AppleWebKit") > -1,
            gecko: a.indexOf("Gecko") > -1 && a.indexOf("KHTML") == -1,
            mobile: !!a.match(/AppleWebKit.*Mobile.*/),
            ios: !!a.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
            android: a.indexOf("Android") > -1 || a.indexOf("Linux") > -1,
            iPhone: a.indexOf("iPhone") > -1 || a.indexOf("Mac") > -1,
            iPad: a.indexOf("iPad") > -1,
            webApp: a.indexOf("Safari") == -1
        }
    }()
};
var secretKey = "8811d44df3c0b408f6fa4a31002db44d";
$(function () {
    var path_array = ["/", "/showroom", "/crowds", "/custom", "/star"];
    var path = location.pathname;
    var not_show_top_ad = $.cookie("not_show_top_ad");
    var not_show_top_big_ad = $.cookie("not_show_top_big_ad");
    var not_show_newpop = $.cookie("not_show_newpop");
    if ($("#navs-active")) {
        var winWidth, prolen, hotpro = $("#navs-active").next(".row").children(".col-4");
        prolen = hotpro.size();
        window.innerWidth ? winWidth = window.innerWidth : winWidth = document.body.clientWidth;
        var changem = function () {
            if (parseInt(winWidth) > 1350 && parseInt(winWidth) < 1700) {
                prolen = 24
            } else {
                if (parseInt(winWidth) < 1350) {
                    prolen = 25
                } else {
                    prolen = 28
                }
            }
            hotpro.each(function (i, d) {
                if ((i + 1) > prolen) {
                    $(this).hide()
                } else {
                    $(this).show()
                }
            })
        };
        $(window).resize(function () {
            window.innerWidth ? winWidth = window.innerWidth : winWidth = document.body.clientWidth;
            changem()
        });
        changem()
    }
    if ($(".place-holder-keyword").val() == "") {
        $(".place-holder-keyword").val("")
    }

    function search_form_submit() {
        $("#header-search-form").submit()
    }

    $("#header-keyword").autocomplete("/membersearch.json", {
        width: (parseInt($("#header-keyword").width()) + 16),
        onItemSelect: search_form_submit,
        cacheLength: 1,
        maxItemsToShow: 10,
        autoFill: false
    });
    if ($(".navi-category-main").hasClass("display-none")) {
        $(".navi-category").hover(function () {
            $(".navi-category-main").slideDown()
        }, function () {
            $(".navi-category-main").hide()
        })
    }
    $(".scroll-top").click(function () {
        $("html, body").animate({scrollTop: 0}, 300);
        return false
    });
    $(window).bind("scroll", function () {
        var scroll_top = $(window).scrollTop();
        if ($(".navi-category-main").hasClass("display-block")) {
            if (scroll_top > 785) {
                $(".navi-category-main").hide()
            } else {
                $(".navi-category-main").show()
            }
        }
        if (scroll_top > 60) {
            $(".scroll-item").show()
        } else {
            $(".scroll-item").hide()
        }
    });
    if ($(".scroll-suspend").size() > 0) {
        var suspendScrollAnchor = function (this_obj, scroll_top) {
            var obj_point = new Array(), obj_point_top = new Array(), obj_point_scope = new Array();
            $.each(this_obj.find("a"), function (i, d) {
                var id = $(d).attr("href");
                if (id.indexOf("#") != -1) {
                    if ($(id).size() == 0) {
                    } else {
                        obj_point_top.push($(id).offset().top - 64);
                        obj_point_scope.push($(id).offset().top + $(id).height() - 64);
                        obj_point.push(id)
                    }
                }
            });
            this_obj.find("a").removeClass("on");
            var i = 0;
            $.each(obj_point, function (index, objs) {
                if (scroll_top >= obj_point_top[index]) {
                    i = index
                }
            });
            this_obj.find("a[href=" + obj_point[i] + "]").addClass("on")
        };
        $(".scroll-suspend[data-anchor=true] a").click(function () {
            if ($(this).attr("href").indexOf("#") != -1) {
                var height = $(this).attr("data-height");
                if (isNaN(height)) {
                    height = 0
                }
                height = parseInt(height);
                var id = $(this).attr("href");
                var obj_height = $(id).offset().top;
                if (height > 0) {
                    $("html, body").animate({scrollTop: (obj_height - 30 - height)}, 300)
                } else {
                    $("html, body").animate({scrollTop: obj_height - 30}, 300)
                }
                return false
            }
        });
        $.each($(".scroll-suspend"), function (i, obj) {
            var offset_top = $(obj).offset().top, suspend_bar = $(obj).attr("data-scroll-bg"),
                scroll_end = $(obj).attr("data-scroll-end"), scroll_holder = $(obj).attr("data-scroll-holder"),
                anchor = $(obj).attr("data-anchor"), data_top = $(obj).attr("data-offset");
            var page_type = $(".banner-home").attr("data-pagetype");
            if (!data_top) {
                data_top = 0
            }
            if (scroll_end) {
                $(window).bind("scroll", function () {
                    var scroll_top = $(window).scrollTop();
                    var end_top = $("#" + scroll_end).offset().top - $(obj).height();
                    if (scroll_top > offset_top - data_top && scroll_top < end_top) {
                        $(obj).addClass("suspend-fixed");
                        suspend_bar && $("#" + suspend_bar).show();
                        scroll_holder && $("#" + scroll_holder).show();
                        $(obj).find(".navi-category").removeClass("navi-category-border");
                        if (typeof (page_type) != "undefined") {
                            if ($(".navigation").hasClass("suspend-fixed")) {
                                $(".float-search").show()
                            }
                        }
                    } else {
                        $(obj).find(".navi-category").addClass("navi-category-border");
                        $(obj).removeClass("suspend-fixed");
                        if (typeof (page_type) != "undefined") {
                            if ($(".navigation").hasClass("suspend-fixed")) {
                                $(".float-search").show()
                            }
                        }
                        suspend_bar && $("#" + suspend_bar).hide();
                        scroll_holder && $("#" + scroll_holder).hide()
                    }
                    anchor && suspendScrollAnchor($(obj), scroll_top)
                })
            } else {
                $(window).bind("scroll", function () {
                    var scroll_top = $(window).scrollTop();
                    if (scroll_top > offset_top - data_top) {
                        $(obj).addClass("suspend-fixed");
                        suspend_bar && $("#" + suspend_bar).show();
                        scroll_holder && $("#" + scroll_holder).show();
                        $(obj).find(".navi-category").removeClass("navi-category-border");
                        if (typeof (page_type) != "undefined") {
                            if ($(".navigation").hasClass("suspend-fixed")) {
                                $(".float-search").show()
                            }
                        }
                    } else {
                        $(obj).find(".navi-category").addClass("navi-category-border");
                        $(obj).removeClass("suspend-fixed");
                        suspend_bar && $("#" + suspend_bar).hide();
                        scroll_holder && $("#" + scroll_holder).hide();
                        if (typeof (page_type) != "undefined") {
                            if (!$(".navigation").hasClass("suspend-fixed")) {
                                $(".float-search").hide()
                            }
                        }
                    }
                    anchor && suspendScrollAnchor($(obj), scroll_top)
                })
            }
        })
    }
    $(".service-suspend .service-phone").hover(function () {
        $(".service-phone-detail").addClass("show")
    }, function () {
        $(".service-phone-detail").removeClass("show")
    });
    $(".service-suspend .text-info").hover(function () {
        $(this).find("a").addClass("show")
    }, function () {
        $(this).find("a").removeClass("show")
    });
    $(".lazyload,.lazyload img").lazyload();
    $(".validate-form").die().live("submit", function () {
        var form = $(this);
        var url = form.attr("action"), success_tip = form.attr("success-tip"), fail_tip = form.attr("fail-tip"),
            confirm_text = form.attr("confirm"), call_function = form.attr("call-back"),
            redirect_url = form.attr("redirect-url"), target = form.attr("target"),
            is_confirm = $("body").data("is_confirm_form");
        if (form.utilValidateForm()) {
            if (confirm_text && !is_confirm) {
                jConfirm(confirm_text, "", function (r) {
                    if (r) {
                        if (target == undefined) {
                            $.ajax({
                                url: url,
                                data: form.serialize(),
                                type: form.attr("method"),
                                dataType: "json",
                                beforeSend: function () {
                                    form.find("button[type=submit],input[type=submit]").attr("disabled", true)
                                },
                                success: function (data) {
                                    setTimeout(function () {
                                        form.find("button[type=submit],input[type=submit]").removeAttr("disabled")
                                    }, 2000);
                                    if (data.result.status == -2) {
                                        if ($("#random-code-insert").html() == "") {
                                            var random_code_html = template("random-code-template", {timestr: new Date().getTime()});
                                            $("#random-code-insert").html(random_code_html)
                                        }
                                    }
                                    if (data.result.status == 1) {
                                        if (success_tip != "false") {
                                            if (!success_tip) {
                                                success_tip = data.result.message
                                            }
                                            success_tip != "" && $.flashTip(success_tip, "success")
                                        }
                                        if (call_function) {
                                            if (call_function != false) {
                                                try {
                                                    $("body").data("return_data", data);
                                                    if (typeof (eval(call_function)) == "function") {
                                                        eval(call_function + "()")
                                                    }
                                                } catch (e) {
                                                    alert("不存在" + call_function + "这个函数");
                                                    return false
                                                }
                                            }
                                        } else {
                                            setTimeout(function () {
                                                redirect_url ? location.href = redirect_url : location.reload()
                                            }, 1000)
                                        }
                                    } else {
                                        if (!fail_tip) {
                                            fail_tip = data.result.message
                                        }
                                        $.flashTip(fail_tip, "warning");
                                        $(".validate-img").trigger("click")
                                    }
                                }
                            })
                        } else {
                            $("body").data("is_confirm_form", true);
                            form.submit()
                        }
                    }
                });
                return false
            } else {
                if (target == undefined) {
                    $.ajax({
                        url: url,
                        data: form.serialize(),
                        type: form.attr("method"),
                        dataType: "json",
                        beforeSend: function () {
                            form.find("button[type=submit],input[type=submit]").attr("disabled", true)
                        },
                        success: function (data) {
                            setTimeout(function () {
                                form.find("button[type=submit],input[type=submit]").removeAttr("disabled")
                            }, 2000);
                            if (data.result.status == -2) {
                                if ($("#random-code-insert").html() == "") {
                                    var random_code_html = template("random-code-template", {timestr: new Date().getTime()});
                                    $("#random-code-insert").html(random_code_html)
                                }
                            }
                            if (data.result.status == 1) {
                                if (success_tip != "false") {
                                    if (!success_tip) {
                                        success_tip = data.result.message
                                    }
                                    success_tip != "" && $.flashTip(success_tip, "success")
                                }
                                if (call_function) {
                                    if (call_function != false) {
                                        try {
                                            $("body").data("return_data", data);
                                            if (typeof (eval(call_function)) == "function") {
                                                eval(call_function + "()")
                                            }
                                        } catch (e) {
                                            alert("不存在" + call_function + "这个函数");
                                            return false
                                        }
                                    }
                                } else {
                                    setTimeout(function () {
                                        redirect_url ? location.href = redirect_url : location.reload()
                                    }, 1000)
                                }
                            } else {
                                if (!fail_tip) {
                                    fail_tip = data.result.message
                                }
                                $.flashTip(fail_tip, "warning");
                                $(".validate-img").trigger("click")
                            }
                        }
                    });
                    return false
                }
            }
        } else {
            return false
        }
    });
    $(".ajax-request").die().live("click", function () {
        var obj = $(this);
        var obj_id = obj.attr("id"), data_url = obj.attr("data-url"), title = obj.attr("title");
        template_url = obj.attr("template-url"), request_url = obj.attr("request-url"), template_id = obj.attr("template-id"), method_type = obj.attr("method-type"), data_param = obj.attr("data-param"), confirm_text = obj.attr("confirm"), modal_type = obj.attr("modal-type"), modal_width = obj.attr("modal-width"), success_tip = obj.attr("success-tip"), fail_tip = obj.attr("fail-tip"), call_function = obj.attr("call-back"), redirect_url = obj.attr("redirect-url");
        if (!method_type) {
            method_type = "get"
        }
        if (!template_url && !template_id) {
            if (confirm_text) {
                jConfirm(confirm_text, "", function (r) {
                    if (r) {
                        $.ajax({
                            url: data_url,
                            type: method_type,
                            data: data_param ? eval("(" + data_param + ")") : {},
                            dataType: "json",
                            success: function (data) {
                                if (data.result.login == false) {
                                    $("body").data("function", '$("#' + obj_id + '").trigger("click")');
                                    userLogin();
                                    return false
                                }
                                if (data.result.status == 1) {
                                    if (success_tip != "false") {
                                        if (!success_tip) {
                                            success_tip = data.result.message
                                        }
                                        $.flashTip(success_tip, "success")
                                    }
                                    if (call_function) {
                                        if (call_function != false) {
                                            try {
                                                if (typeof (eval(call_function)) == "function") {
                                                    eval(call_function + "()")
                                                }
                                            } catch (e) {
                                                alert("不存在" + call_function + "这个函数");
                                                return false
                                            }
                                        }
                                    } else {
                                        setTimeout(function () {
                                            redirect_url ? location.href = redirect_url : location.reload()
                                        }, 1000)
                                    }
                                } else {
                                    if (!fail_tip) {
                                        fail_tip = data.result.message
                                    }
                                    $.flashTip(fail_tip, "warning")
                                }
                            }
                        })
                    }
                })
            } else {
                $.ajax({
                    url: data_url,
                    data: data_param ? eval("(" + data_param + ")") : {},
                    type: method_type,
                    dataType: "json",
                    success: function (data) {
                        if (data.result.login == false) {
                            $("body").data("function", '$("#' + obj_id + '").trigger("click")');
                            userLogin();
                            return false
                        }
                        if (data.result.status == 1) {
                            if (success_tip != "false") {
                                if (!success_tip) {
                                    success_tip = data.result.message
                                }
                                $.flashTip(success_tip, "success")
                            }
                            if (call_function) {
                                if (call_function != false) {
                                    try {
                                        if (typeof (eval(call_function)) == "function") {
                                            eval(call_function + "()")
                                        }
                                    } catch (e) {
                                        alert("不存在" + call_function + "这个函数");
                                        return false
                                    }
                                }
                            } else {
                                setTimeout(function () {
                                    redirect_url ? location.href = redirect_url : location.reload()
                                }, 1000)
                            }
                        } else {
                            if (!fail_tip) {
                                fail_tip = data.result.message
                            }
                            $.flashTip(fail_tip, "warning")
                        }
                    }
                })
            }
            return false
        } else {
            var data = {};
            if (template_url) {
                $.get(template_url, function (data) {
                    if (modal_type == "pop") {
                        if (!modal_width) {
                            modal_width = 450
                        }
                        $.popModal({content: data, width: modal_width})
                    } else {
                        obj.floatModal({str: data, title: title, direction: "right"})
                    }
                })
            }
            if (request_url) {
                $.getJSON(request_url, function (result) {
                    data = result.result.datas;
                    data.url = data_url;
                    var html = template(template_id, data);
                    if (modal_type == "pop") {
                        if (!modal_width) {
                            modal_width = 450
                        }
                        $.popModal({content: html, width: modal_width})
                    } else {
                        obj.floatModal({str: html, title: title, direction: "right"})
                    }
                })
            }
            if (!request_url && template_id) {
                if (data_param) {
                    data = eval("(" + data_param + ")")
                }
                data.url = data_url;
                var html = template(template_id, data);
                if (modal_type == "pop") {
                    if (!modal_width) {
                        modal_width = 450
                    }
                    $.popModal({content: html, width: modal_width})
                } else {
                    obj.floatModal({str: html, title: title, direction: "right"})
                }
            }
            return false
        }
    });
    $(".validate-send").die().live("click", function () {
        var obj = $(this);
        var account_obj = $(".validate-account");
        var account = account_obj.val();
        var nation_code = $(".mobile-code").val();
        var account_type = account_obj.attr("data-rule");
        var register = obj.attr("data-register");
        var type = obj.attr("data-type") || "";
        if (account == "") {
            account_obj.focus();
            return false
        }
        var source = obj.attr("data-source");
        if (source == "" || source == undefined) {
            source = account
        }
        if (account_type == "mobile") {
            if (!account_obj.utilValidateMobile()) {
                account_obj.focus();
                if (account_obj.siblings(".tip-validate").size() > 0) {
                    account_obj.siblings(".tip-validate").show().text("请输入正确的手机号")
                } else {
                    jAlert("请输入正确的手机号")
                }
                return false
            } else {
                account_obj.siblings(".tip-validate").hide()
            }
            sendCode(account, source, type, nation_code, this)
        } else {
            if (!account_obj.utilValidateMobile() && !account_obj.utilValidateEmail()) {
                account_obj.focus();
                if (account_obj.siblings(".tip-validate").size() > 0) {
                    account_obj.siblings(".tip-validate").show().text("请输入正确手机号码或邮箱")
                } else {
                    jAlert("请输入正确手机号码或邮箱")
                }
                return false
            } else {
                account_obj.siblings(".tip-validate").hide()
            }
        }
        var test = function (o) {
            $.ajax({
                url: "/member/forgetLoginCode",
                type: "post",
                data: {loginCode: account},
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data.result.status == -1) {
                        o = false;
                        jAlert("请输入已经注册的手机号")
                    }
                }
            });
            return o
        };
        return false
    });
    $(".validate-img").die().live("click", function () {
        var url = $(this).attr("src");
        if (url.indexOf("time=") >= 0 && url.indexOf("?") >= 0) {
            url = url.replace("&time=", "time=");
            var arr = url.split("time=");
            url = arr[0]
        }
        if (url.indexOf("?") >= 0) {
            url += "&"
        } else {
            url += "?"
        }
        url = url.replace("?&", "?");
        $(this).attr("src", url + "time=" + new Date().getTime() + "&mobile=" + $(".validate-account").val());
        return false
    });
    $(".remind-send").click(function () {
        var mobile = $(this).siblings(".mobile");
        var param = $(this).siblings(".remind-param").serialize();
        if (mobile.val() == mobile.attr("placeholder") || mobile.val() == "") {
            mobile.val("").focus();
            return false
        }
        if (!mobile.utilValidateMobile() && !mobile.utilValidateEmail()) {
            mobile.focus();
            jAlert("手机号码格式不正确");
            return false
        } else {
            $.post("/remind", param, function (data) {
                if (data.result.status == 1) {
                    jAlert("您已经成功添加了短信或邮箱提醒。")
                } else {
                    jAlert(data.result.message)
                }
            }, "json")
        }
        return false
    });
    $(".excel-export").click(function () {
        var form = $(this).parents("form");
        var url = $(this).attr("data-url");
        var _this = $(this);
        var _text = _this.text();
        jConfirm("确定要导出报表吗？", "", function (r) {
            if (r) {
                _this.attr("disabled", true).text("正在生成数据...");
                $.ajax({
                    url: url,
                    type: form.attr("method"),
                    data: form.serialize(),
                    dataType: "json",
                    success: function (data) {
                        _this.removeAttr("disabled").text(_text);
                        if (data.result.status > 0) {
                            var paths = data.result.data.paths;
                            if (paths.length > 1) {
                                var str = "数据较大，分成多个文件下载：<br />";
                                for (var i = 0; i < paths.length; i++) {
                                    str += ' &nbsp;<a href="' + paths[i] + '">文件' + (i + 1) + "</a> &nbsp;"
                                }
                                window.parent.jAlert(str)
                            } else {
                                window.location.href = paths[0]
                            }
                        } else {
                            window.parent.jAlert(data.result.message)
                        }
                    },
                    statusCode: {
                        404: function () {
                            alert("服务端发生错误")
                        }, 500: function () {
                            alert("服务端发生错误")
                        }
                    },
                    error: function () {
                    }
                })
            }
        });
        return false
    });
    $(".share-box a.share").click(function () {
        var appkey = new Array();
        appkey.sina = "1698482307";
        appkey.qq = "";
        appkey.qzone = "";
        var obj = $(this).parent();
        var config = eval("(" + obj.attr("data-config") + ")");
        if (config.url == "" || config.url == undefined) {
            config.url = location.href
        }
        if (config.title == "" || config.title == undefined) {
            config.title = document.title
        }
        if (config.desc == undefined) {
            config.desc = ""
        }
        var type = $(this).attr("class").replace("share ", "");
        var open_url;
        if (type == "weixin") {
            $.getScript("/static/nc/js/utils/jquery.qrcode.js", function () {
                obj.find(".barcode").html("");
                var str = '<div class="share-to-weixin">					<p class="title"><a class="float-right" style="font-size:16px;" href="javascript:" onclick="$(this).parent().parent().remove();"><i class="fa fa-close"></i></a>分享给微信好友和朋友圈</p>					<p class="barcode"></p>					<p class="info">打开微信，点击“发现”，使用 “扫一扫”扫描二维码，打开网页，点击右上角分享到我的朋友圈。</p>					</div>';
                obj.append(str);
                obj.find(".barcode").qrcode({width: 200, height: 200, text: config.url})
            });
            return false
        }
        if (type == "weibo") {
            open_url = "//service.weibo.com/share/share.php?url=" + encodeURIComponent(config.url) + "&title=" + encodeURIComponent(config.title) + "&pic=" + encodeURIComponent(config.pic) + "&appkey=1698482307&searchPic=false"
        }
        if (type == "qq") {
            open_url = "//connect.qq.com/widget/shareqq/index.html?url=" + encodeURIComponent(config.url) + "&title=" + encodeURIComponent(config.title) + "&pic=" + encodeURIComponent(config.pic) + "&summary=&site=d2cmall"
        }
        if (type == "qzone") {
            open_url = "//sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=" + encodeURIComponent(config.url) + "&title=" + encodeURIComponent(config.title) + "&desc=" + encodeURIComponent(config.desc) + "&pic=" + encodeURIComponent(config.pic) + "&summary=&site="
        }
        if (type == "renren") {
            open_url = "//widget.renren.com/dialog/share?resourceUrl=" + encodeURIComponent(config.url) + "&title=" + encodeURIComponent(config.title) + "&description=" + encodeURIComponent(config.desc) + "&pic=" + encodeURIComponent(config.pic)
        }
        if (type == "douban") {
            open_url = "//www.douban.com/share/service?href=" + encodeURIComponent(config.url) + "&name=" + encodeURIComponent(config.title) + "&text=" + encodeURIComponent(config.desc) + "&image=" + encodeURIComponent(config.pic)
        }
        if (type == "tqq") {
            open_url = "//share.v.t.qq.com/index.php?c=share&a=index&url=" + encodeURIComponent(config.url) + "&title=" + encodeURIComponent(config.title) + "&text=" + encodeURIComponent(config.desc) + "&pic=" + encodeURIComponent(config.pic)
        }
        window.open(open_url);
        return false
    });
    if ($(".count-down").size() > 0) {
        $(".count-down").each(function () {
            $(this).countdown()
        })
    }
    if ($("#cart-nums-id").size() > 0) {
        if ($("#loginStatus").size() > 0) {
            countCart()
        }
    }
    var country_template = '{{each list as item i}}<div class="option-item" style="background:transparent;">{{item.group}}</div>{{each item.list as country ii}}<div class="option-item country-item" data-code="{{country.mobileCode}}" data-name="{{country.cnName}}" data-displayname="{{if item.group!=\'常用地区\'}}{{country.enName}} {{/if}}{{country.cnName}}">    <label>{{if item.group!=\'常用地区\'}}{{country.enName}} {{/if}}{{country.cnName}}</label>    <span class="note grey">{{country.mobileCode}}</span></div>{{/each}}{{/each}}';
    $("#choose-country").die().live("click", function (e) {
        if ($(".country-data").size() == 0) {
            $(this).after('<div class="country-data"></div>')
        } else {
            $(".country-data").toggle()
        }
        if ($(".country-data").html() == "") {
            $.getScript("/static/nc/js/utils/json-country.js", function (result) {
                var data = eval(result);
                var countrys = new Array();
                var group = null;
                var m = 0;
                $.each(data, function (i, d) {
                    if (d.isTop && d.group == undefined) {
                        if (countrys[0] == undefined) {
                            countrys[0] = {}
                        }
                        countrys[0].group = "常用地区";
                        if (countrys[0].list == undefined) {
                            countrys[0].list = []
                        }
                        countrys[0].list.push(d)
                    } else {
                        if (group != d.group) {
                            m++
                        }
                        group = d.group;
                        if (countrys[m] == undefined) {
                            countrys[m] = {}
                        }
                        countrys[m].group = group;
                        if (countrys[m].list == undefined) {
                            countrys[m].list = []
                        }
                        countrys[m].list.push(d)
                    }
                });
                var render = template.compile(country_template);
                var html = render({list: countrys});
                $(".country-data").html(html).show()
            })
        }
        return false
    });
    $("body").click(function (e) {
        if (e.currentTarget.id != "choose-country") {
            $(".country-data").hide()
        }
    });
    $(".country-item").die().live("click", function () {
        var obj = $(this);
        var code = obj.attr("data-code"), name = obj.attr("data-name"), displayname = obj.attr("data-displayname");
        $("#mobile-code").text("+" + code);
        $(".country-code").val(name);
        $(".mobile-code").val(code);
        $(".country-data").hide();
        return false
    });
    $(".video-player").click(function () {
        var video_path = $(this).attr("data-video");
        var video_apple_path = $(this).attr("data-apple-video");
        var str = "";
        if (!browser.versions.iPad || video_apple_path == undefined) {
            str = '<embed width="100%" height="100%" pluginspage="//www.macromedia.com/go/getflashplayer" src="//static.d2c.cn/common/o/flvplayer.swf" type="application/x-shockwave-flash" allowfullscreen="true" flashvars="vcastr_file=' + video_path + '&amp;LogoText=&amp;IsAutoPlay=1" quality="high" />'
        } else {
            str = '<video src="' + video_apple_path + '" width="100%" height="100%" autoplay="autoplay" style="background:#000;"></video>'
        }
        $(this).parent().html(str);
        return false
    });
    $.each($(".video-player"), function (i, d) {
        if ($(d).attr("data-auto") == "1") {
            $(d).trigger("click")
        }
    });
    if ($(".activity-form-d2c").size() > 0) {
        $.ajax({
            url: "/member/check", type: "get", dataType: "json", success: function (data) {
                if (data.result.status == 1) {
                    var mobile = data.result.datas.mobile;
                    if (mobile != "") {
                        $(".use_mobile").val(mobile).attr("readonly", "readonly")
                    }
                }
            }
        });
        $(".activity-form-d2c").submit(function () {
            couponFormSubmit($(this))
        })
    }
});
var userLogin = function () {
    var a = "/member/login?" + new Date().getTime();
    $.popModal({url: a, datatype: "", width: "450px"});
    return false
};
var countCart = function () {
    $.getJSON("/cart/count?t=" + Math.random(), function (a) {
        if (a.cartItemCount > 0) {
            $("#cart-nums-id").text("(" + a.cartItemCount + ")");
            $("#cart-nums-id").data("change", 1)
        }
    })
};

function loginSuccessReturn() {
    var action = $("body").data("function");
    if (action) {
        $.popModalClose();
        setTimeout(eval("{" + action + "}"), 1000)
    } else {
        location.reload()
    }
}

function registerSuccessReturn() {
    var action = $("body").data("function");
    if (action) {
        $.popModalClose();
        setTimeout(eval("{" + action + "}"), 1000)
    } else {
        location.reload()
    }
}

function sendCode(i, a, j, f, b) {
    $(b).attr("disabled", true);
    var e = hex_md5("mobile=" + i + secretKey);
    var c = $.base64("encode", "mobile=" + i + "&sign=" + e);
    var h = {mobile: i, source: a, type: j, terminal: "PC", nationCode: f, appParams: c};
    var d = 60;
    var g = $(".validate-send");
    $.ajax({
        url: "/sms/send/encrypt", type: "post", data: h, dataType: "json", success: function (k) {
            if (k.result.status == -2) {
                $(".validate-img").trigger("click")
            }
            if (k.result.status == 1) {
                g.attr("disabled", "disabled");
                $("#box-modal-remove").trigger("click");
                var l = setInterval(function () {
                    if (d < 1) {
                        clearInterval(l);
                        g.removeAttr("disabled").text("重发验证码")
                    } else {
                        g.text(d + "秒后重发");
                        d--
                    }
                }, 1000)
            } else {
                $(b).removeAttr("disabled");
                $.flashTip(k.result.message, "warning")
            }
        }
    });
    return false
}

$(".promotion-coupon").on("click", function () {
    var a = $(this).attr("data-id");
    $.get("/coupon/batch/" + a, function (b) {
        if (b.result.status == 1) {
            $.flashTip("领取成功", "success")
        } else {
            if (b.result.login == false) {
                userLogin();
                return false
            } else {
                $.flashTip(b.result.message, "warning")
            }
        }
    }, "json")
});

function couponFormSubmit(b) {
    var a = b.find("input[name=mobile]");
    if (a != "") {
        $("body").data("activity_form", b.serializeObject());
        $.ajax({
            url: "/member/check", type: "get", data: b.serialize(), dataType: "json", success: function (c) {
                if (c.result.status == 1) {
                    activityExcute(b.serializeObject())
                } else {
                    activityRegister(b.serializeObject())
                }
            }
        })
    }
    return false
}

function activityExcuteSuccess(f, a) {
    $.popModalClose();
    var e = '<div>                    <div class="to-cart-success" style="background:rgba(255,255,255,0.95)">                    <div class="cart-modal-content"><h2 style="color:#FF6600;">恭喜您获得专属D2C红包{{amount}}元 </h2>                    <p class="txt-note">可在我的优惠券中查看或下单时选择抵扣。请转发此页面，让您的朋友也试试手气。</p></div>                    <div class="cart-modal-button"><button type="button" class="button button-s button-green" style="width:70px;" onclick="$.popModalClose();">确定</button>                    </div></div></div>';
    if (a.template == "" || a.template == undefined) {
        var d = template.compile(e);
        var b = {amount: f.result.datas.coupon.amount};
        var c = d(b);
        $.popModal({content: c, datatype: "", width: "350"});
        return false
    } else {
        var c = template(a.template);
        $("#success-lottery").append(c)
    }
}

function activityExcute(a) {
    if (a == "" || a == undefined) {
        a = $("body").data("activity_form")
    }
    $.ajax({
        url: a.url, type: "post", data: {mobile: a.mobile}, dataType: "json", success: function (b) {
            if (b.result.status == 1) {
                if (a.mobile != "") {
                    $(".use_mobile").attr("readonly", "readonly")
                }
                activityExcuteSuccess(b, a)
            } else {
                $.flashTip(b.result.message, "error")
            }
        }
    })
}

function activityRegister(a) {
    var c = '<div class="form">                <h3 style="text-align:center;font-size:16px;top:-4px" class="pop-title">{{title}}</h3>                    <form name="modal-reg-form" class="validate-form" method="post" action="/member/join/d2c" call-back="activityExcute" success-tip="false">                        <div class="form-item form-item-vertical">                            <label>请输入手机号</label>                            <div class="form-item-mobile" style="width:100%;">                                <span class="choose-country" id="choose-country" style="position:absolute;line-height:36px;"><span id="mobile-code"><strong>+86</strong></span><span class="fa fa-caret-down" style="top:11px;"><em></em></span></span>                                <input type="text" name="mobile" id="mobile" minlength="2" maxlength="20" value="{{mobile}}" placeholder="" data-rule="mobile" readonly="readonly" class="input input-l validate-account">                            </div>                            <input type="hidden" name="nationCode" value="86" class="mobile-code">                            <input type="hidden" name="source" value="{{source}}" class="activityRegister-source">                            <div class="tip tip-validate" data-target="reg_login_code" data-rule="mobile"></div>                        </div>                        <div class="form-item form-item-vertical">                            <label>请输入短信校验码</label>                            <input type="text" name="code" id="validate-code" size="18" title="短信校验码" placeholder="" class="input input-l" style="width:58%;" value="">                            <button type="button" data-source="" data-type="Member" class="button button-l button-green validate-send" style="width:40%">获取校验码</button>                            <div class="tip tip-validate" data-target="validate-code"></div>                        </div>                        <div class="form-button" style="margin-bottom:20px">                            <button type="submit" class="button button-l button-red" style="width:60%;">加入D2C</button>                        </div>                    </form>                </div>';
    var b = {title: a.title, mobile: a.mobile, source: a.source};
    var e = template.compile(c);
    var d = e(b);
    $.popModal({content: d, datatype: "", width: "350"});
    return false
}

String.prototype.replaceAll = function (b, a) {
    return this.replace(new RegExp(b, "gm"), a)
};
Date.prototype.pattern = function (a) {
    var d = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12,
        "H+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        S: this.getMilliseconds()
    };
    var c = {"0": "\u65e5", "1": "\u4e00", "2": "\u4e8c", "3": "\u4e09", "4": "\u56db", "5": "\u4e94", "6": "\u516d"};
    if (/(y+)/.test(a)) {
        a = a.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length))
    }
    if (/(E+)/.test(a)) {
        a = a.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f" : "\u5468") : "") + c[this.getDay() + ""])
    }
    for (var b in d) {
        if (new RegExp("(" + b + ")").test(a)) {
            a = a.replace(RegExp.$1, (RegExp.$1.length == 1) ? (d[b]) : (("00" + d[b]).substr(("" + d[b]).length)))
        }
    }
    return a
};

function isEmpty(a) {
    if (a != undefined && a != null && a != "") {
        return false
    } else {
        return true
    }
}

function isweixin() {
    var a = navigator.userAgent.toLowerCase();
    if (a.match(/MicroMessenger/i) == "micromessenger") {
        return true
    } else {
        return false
    }
}

$(document).on("change", ".upload-file", function () {
    var c = '<div class="upload-item" id="{{id}}">        <em class="icon icon-close del"></em>        <span><img src="https://img.d2c.cn{{path}}" width="100%" alt="" /></span>        <input type="hidden" class="path-input" value="{{value}}">    </div>';
    var d = $(this);
    var b = d.parent();
    var a = d.attr("data-upload-url");
    if ($(".upload-item").size() > 9) {
        jAlert("最多只能上传9张图片。");
        return false
    }
    $.getScript("/static/nc/js/utils/jquery.fileupload.js", function () {
        $.ajaxFileUpload({
            url: a,
            secureuri: false,
            fileObject: d,
            dataType: "json",
            timeout: 5000,
            success: function (e) {
                if (e.status == 1) {
                    setTimeout(function () {
                        var g = template.compile(c);
                        var f = g(e);
                        b.before(f)
                    }, 600)
                }
            }
        })
    })
});
$(document).on("click", ".upload-item .del", function () {
    var a = $(this).parent();
    jConfirm("确定要删除该图片吗？", "", function (b) {
        if (b) {
            a.remove()
        }
    });
    return false
});
;

;
