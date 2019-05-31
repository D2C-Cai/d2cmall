/*! jQuery v1.11.3 | (c) 2005, 2015 jQuery Foundation, Inc. | jquery.org/license */
!function (d, c) {
    "object" == typeof module && "object" == typeof module.exports ? module.exports = d.document ? c(d, !0) : function (b) {
        if (!b.document) {
            throw new Error("jQuery requires a window with a document")
        }
        return c(b)
    } : c(d)
}("undefined" != typeof window ? window : this, function (a, b) {
    var c = [], d = c.slice, e = c.concat, f = c.push, g = c.indexOf, h = {}, i = h.toString, j = h.hasOwnProperty,
        k = {}, l = "1.11.3", m = function (a, b) {
            return new m.fn.init(a, b)
        }, n = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, o = /^-ms-/, p = /-([\da-z])/gi, q = function (a, b) {
            return b.toUpperCase()
        };
    m.fn = m.prototype = {
        jquery: l, constructor: m, selector: "", length: 0, toArray: function () {
            return d.call(this)
        }, get: function (a) {
            return null != a ? 0 > a ? this[a + this.length] : this[a] : d.call(this)
        }, pushStack: function (a) {
            var b = m.merge(this.constructor(), a);
            return b.prevObject = this, b.context = this.context, b
        }, each: function (a, b) {
            return m.each(this, a, b)
        }, map: function (a) {
            return this.pushStack(m.map(this, function (b, c) {
                return a.call(b, c, b)
            }))
        }, slice: function () {
            return this.pushStack(d.apply(this, arguments))
        }, first: function () {
            return this.eq(0)
        }, last: function () {
            return this.eq(-1)
        }, eq: function (a) {
            var b = this.length, c = +a + (0 > a ? b : 0);
            return this.pushStack(c >= 0 && b > c ? [this[c]] : [])
        }, end: function () {
            return this.prevObject || this.constructor(null)
        }, push: f, sort: c.sort, splice: c.splice
    }, m.extend = m.fn.extend = function () {
        var a, b, c, d, e, f, g = arguments[0] || {}, h = 1, i = arguments.length, j = !1;
        for ("boolean" == typeof g && (j = g, g = arguments[h] || {}, h++), "object" == typeof g || m.isFunction(g) || (g = {}), h === i && (g = this, h--); i > h; h++) {
            if (null != (e = arguments[h])) {
                for (d in e) {
                    a = g[d], c = e[d], g !== c && (j && c && (m.isPlainObject(c) || (b = m.isArray(c))) ? (b ? (b = !1, f = a && m.isArray(a) ? a : []) : f = a && m.isPlainObject(a) ? a : {}, g[d] = m.extend(j, f, c)) : void 0 !== c && (g[d] = c))
                }
            }
        }
        return g
    }, m.extend({
        expando: "jQuery" + (l + Math.random()).replace(/\D/g, ""), isReady: !0, error: function (a) {
            throw new Error(a)
        }, noop: function () {
        }, isFunction: function (a) {
            return "function" === m.type(a)
        }, isArray: Array.isArray || function (a) {
            return "array" === m.type(a)
        }, isWindow: function (a) {
            return null != a && a == a.window
        }, isNumeric: function (a) {
            return !m.isArray(a) && a - parseFloat(a) + 1 >= 0
        }, isEmptyObject: function (a) {
            var b;
            for (b in a) {
                return !1
            }
            return !0
        }, isPlainObject: function (a) {
            var b;
            if (!a || "object" !== m.type(a) || a.nodeType || m.isWindow(a)) {
                return !1
            }
            try {
                if (a.constructor && !j.call(a, "constructor") && !j.call(a.constructor.prototype, "isPrototypeOf")) {
                    return !1
                }
            } catch (c) {
                return !1
            }
            if (k.ownLast) {
                for (b in a) {
                    return j.call(a, b)
                }
            }
            for (b in a) {
            }
            return void 0 === b || j.call(a, b)
        }, type: function (a) {
            return null == a ? a + "" : "object" == typeof a || "function" == typeof a ? h[i.call(a)] || "object" : typeof a
        }, globalEval: function (b) {
            b && m.trim(b) && (a.execScript || function (b) {
                a.eval.call(a, b)
            })(b)
        }, camelCase: function (a) {
            return a.replace(o, "ms-").replace(p, q)
        }, nodeName: function (a, b) {
            return a.nodeName && a.nodeName.toLowerCase() === b.toLowerCase()
        }, each: function (a, b, c) {
            var d, e = 0, f = a.length, g = r(a);
            if (c) {
                if (g) {
                    for (; f > e; e++) {
                        if (d = b.apply(a[e], c), d === !1) {
                            break
                        }
                    }
                } else {
                    for (e in a) {
                        if (d = b.apply(a[e], c), d === !1) {
                            break
                        }
                    }
                }
            } else {
                if (g) {
                    for (; f > e; e++) {
                        if (d = b.call(a[e], e, a[e]), d === !1) {
                            break
                        }
                    }
                } else {
                    for (e in a) {
                        if (d = b.call(a[e], e, a[e]), d === !1) {
                            break
                        }
                    }
                }
            }
            return a
        }, trim: function (a) {
            return null == a ? "" : (a + "").replace(n, "")
        }, makeArray: function (a, b) {
            var c = b || [];
            return null != a && (r(Object(a)) ? m.merge(c, "string" == typeof a ? [a] : a) : f.call(c, a)), c
        }, inArray: function (a, b, c) {
            var d;
            if (b) {
                if (g) {
                    return g.call(b, a, c)
                }
                for (d = b.length, c = c ? 0 > c ? Math.max(0, d + c) : c : 0; d > c; c++) {
                    if (c in b && b[c] === a) {
                        return c
                    }
                }
            }
            return -1
        }, merge: function (a, b) {
            var c = +b.length, d = 0, e = a.length;
            while (c > d) {
                a[e++] = b[d++]
            }
            if (c !== c) {
                while (void 0 !== b[d]) {
                    a[e++] = b[d++]
                }
            }
            return a.length = e, a
        }, grep: function (a, b, c) {
            for (var d, e = [], f = 0, g = a.length, h = !c; g > f; f++) {
                d = !b(a[f], f), d !== h && e.push(a[f])
            }
            return e
        }, map: function (a, b, c) {
            var d, f = 0, g = a.length, h = r(a), i = [];
            if (h) {
                for (; g > f; f++) {
                    d = b(a[f], f, c), null != d && i.push(d)
                }
            } else {
                for (f in a) {
                    d = b(a[f], f, c), null != d && i.push(d)
                }
            }
            return e.apply([], i)
        }, guid: 1, proxy: function (a, b) {
            var c, e, f;
            return "string" == typeof b && (f = a[b], b = a, a = f), m.isFunction(a) ? (c = d.call(arguments, 2), e = function () {
                return a.apply(b || this, c.concat(d.call(arguments)))
            }, e.guid = a.guid = a.guid || m.guid++, e) : void 0
        }, now: function () {
            return +new Date
        }, support: k
    }), m.each("Boolean Number String Function Array Date RegExp Object Error".split(" "), function (a, b) {
        h["[object " + b + "]"] = b.toLowerCase()
    });

    function r(a) {
        var b = "length" in a && a.length, c = m.type(a);
        return "function" === c || m.isWindow(a) ? !1 : 1 === a.nodeType && b ? !0 : "array" === c || 0 === b || "number" == typeof b && b > 0 && b - 1 in a
    }

    var s = function (a) {
        var b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u = "sizzle" + 1 * new Date, v = a.document, w = 0,
            x = 0, y = ha(), z = ha(), A = ha(), B = function (a, b) {
                return a === b && (l = !0), 0
            }, C = 1 << 31, D = {}.hasOwnProperty, E = [], F = E.pop, G = E.push, H = E.push, I = E.slice,
            J = function (a, b) {
                for (var c = 0, d = a.length; d > c; c++) {
                    if (a[c] === b) {
                        return c
                    }
                }
                return -1
            },
            K = "checked|selected|async|autofocus|autoplay|controls|defer|disabled|hidden|ismap|loop|multiple|open|readonly|required|scoped",
            L = "[\\x20\\t\\r\\n\\f]", M = "(?:\\\\.|[\\w-]|[^\\x00-\\xa0])+", N = M.replace("w", "w#"),
            O = "\\[" + L + "*(" + M + ")(?:" + L + "*([*^$|!~]?=)" + L + "*(?:'((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\"|(" + N + "))|)" + L + "*\\]",
            P = ":(" + M + ")(?:\\((('((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\")|((?:\\\\.|[^\\\\()[\\]]|" + O + ")*)|.*)\\)|)",
            Q = new RegExp(L + "+", "g"), R = new RegExp("^" + L + "+|((?:^|[^\\\\])(?:\\\\.)*)" + L + "+$", "g"),
            S = new RegExp("^" + L + "*," + L + "*"), T = new RegExp("^" + L + "*([>+~]|" + L + ")" + L + "*"),
            U = new RegExp("=" + L + "*([^\\]'\"]*?)" + L + "*\\]", "g"), V = new RegExp(P),
            W = new RegExp("^" + N + "$"), X = {
                ID: new RegExp("^#(" + M + ")"),
                CLASS: new RegExp("^\\.(" + M + ")"),
                TAG: new RegExp("^(" + M.replace("w", "w*") + ")"),
                ATTR: new RegExp("^" + O),
                PSEUDO: new RegExp("^" + P),
                CHILD: new RegExp("^:(only|first|last|nth|nth-last)-(child|of-type)(?:\\(" + L + "*(even|odd|(([+-]|)(\\d*)n|)" + L + "*(?:([+-]|)" + L + "*(\\d+)|))" + L + "*\\)|)", "i"),
                bool: new RegExp("^(?:" + K + ")$", "i"),
                needsContext: new RegExp("^" + L + "*[>+~]|:(even|odd|eq|gt|lt|nth|first|last)(?:\\(" + L + "*((?:-\\d)?\\d*)" + L + "*\\)|)(?=[^-]|$)", "i")
            }, Y = /^(?:input|select|textarea|button)$/i, Z = /^h\d$/i, $ = /^[^{]+\{\s*\[native \w/,
            _ = /^(?:#([\w-]+)|(\w+)|\.([\w-]+))$/, aa = /[+~]/, ba = /'|\\/g,
            ca = new RegExp("\\\\([\\da-f]{1,6}" + L + "?|(" + L + ")|.)", "ig"), da = function (a, b, c) {
                var d = "0x" + b - 65536;
                return d !== d || c ? b : 0 > d ? String.fromCharCode(d + 65536) : String.fromCharCode(d >> 10 | 55296, 1023 & d | 56320)
            }, ea = function () {
                m()
            };
        try {
            H.apply(E = I.call(v.childNodes), v.childNodes), E[v.childNodes.length].nodeType
        } catch (fa) {
            H = {
                apply: E.length ? function (a, b) {
                    G.apply(a, I.call(b))
                } : function (a, b) {
                    var c = a.length, d = 0;
                    while (a[c++] = b[d++]) {
                    }
                    a.length = c - 1
                }
            }
        }

        function ga(a, b, d, e) {
            var f, h, j, k, l, o, r, s, w, x;
            if ((b ? b.ownerDocument || b : v) !== n && m(b), b = b || n, d = d || [], k = b.nodeType, "string" != typeof a || !a || 1 !== k && 9 !== k && 11 !== k) {
                return d
            }
            if (!e && p) {
                if (11 !== k && (f = _.exec(a))) {
                    if (j = f[1]) {
                        if (9 === k) {
                            if (h = b.getElementById(j), !h || !h.parentNode) {
                                return d
                            }
                            if (h.id === j) {
                                return d.push(h), d
                            }
                        } else {
                            if (b.ownerDocument && (h = b.ownerDocument.getElementById(j)) && t(b, h) && h.id === j) {
                                return d.push(h), d
                            }
                        }
                    } else {
                        if (f[2]) {
                            return H.apply(d, b.getElementsByTagName(a)), d
                        }
                        if ((j = f[3]) && c.getElementsByClassName) {
                            return H.apply(d, b.getElementsByClassName(j)), d
                        }
                    }
                }
                if (c.qsa && (!q || !q.test(a))) {
                    if (s = r = u, w = b, x = 1 !== k && a, 1 === k && "object" !== b.nodeName.toLowerCase()) {
                        o = g(a), (r = b.getAttribute("id")) ? s = r.replace(ba, "\\$&") : b.setAttribute("id", s), s = "[id='" + s + "'] ", l = o.length;
                        while (l--) {
                            o[l] = s + ra(o[l])
                        }
                        w = aa.test(a) && pa(b.parentNode) || b, x = o.join(",")
                    }
                    if (x) {
                        try {
                            return H.apply(d, w.querySelectorAll(x)), d
                        } catch (y) {
                        } finally {
                            r || b.removeAttribute("id")
                        }
                    }
                }
            }
            return i(a.replace(R, "$1"), b, d, e)
        }

        function ha() {
            var a = [];

            function b(c, e) {
                return a.push(c + " ") > d.cacheLength && delete b[a.shift()], b[c + " "] = e
            }

            return b
        }

        function ia(a) {
            return a[u] = !0, a
        }

        function ja(a) {
            var b = n.createElement("div");
            try {
                return !!a(b)
            } catch (c) {
                return !1
            } finally {
                b.parentNode && b.parentNode.removeChild(b), b = null
            }
        }

        function ka(a, b) {
            var c = a.split("|"), e = a.length;
            while (e--) {
                d.attrHandle[c[e]] = b
            }
        }

        function la(a, b) {
            var c = b && a,
                d = c && 1 === a.nodeType && 1 === b.nodeType && (~b.sourceIndex || C) - (~a.sourceIndex || C);
            if (d) {
                return d
            }
            if (c) {
                while (c = c.nextSibling) {
                    if (c === b) {
                        return -1
                    }
                }
            }
            return a ? 1 : -1
        }

        function ma(a) {
            return function (b) {
                var c = b.nodeName.toLowerCase();
                return "input" === c && b.type === a
            }
        }

        function na(a) {
            return function (b) {
                var c = b.nodeName.toLowerCase();
                return ("input" === c || "button" === c) && b.type === a
            }
        }

        function oa(a) {
            return ia(function (b) {
                return b = +b, ia(function (c, d) {
                    var e, f = a([], c.length, b), g = f.length;
                    while (g--) {
                        c[e = f[g]] && (c[e] = !(d[e] = c[e]))
                    }
                })
            })
        }

        function pa(a) {
            return a && "undefined" != typeof a.getElementsByTagName && a
        }

        c = ga.support = {}, f = ga.isXML = function (a) {
            var b = a && (a.ownerDocument || a).documentElement;
            return b ? "HTML" !== b.nodeName : !1
        }, m = ga.setDocument = function (a) {
            var b, e, g = a ? a.ownerDocument || a : v;
            return g !== n && 9 === g.nodeType && g.documentElement ? (n = g, o = g.documentElement, e = g.defaultView, e && e !== e.top && (e.addEventListener ? e.addEventListener("unload", ea, !1) : e.attachEvent && e.attachEvent("onunload", ea)), p = !f(g), c.attributes = ja(function (a) {
                return a.className = "i", !a.getAttribute("className")
            }), c.getElementsByTagName = ja(function (a) {
                return a.appendChild(g.createComment("")), !a.getElementsByTagName("*").length
            }), c.getElementsByClassName = $.test(g.getElementsByClassName), c.getById = ja(function (a) {
                return o.appendChild(a).id = u, !g.getElementsByName || !g.getElementsByName(u).length
            }), c.getById ? (d.find.ID = function (a, b) {
                if ("undefined" != typeof b.getElementById && p) {
                    var c = b.getElementById(a);
                    return c && c.parentNode ? [c] : []
                }
            }, d.filter.ID = function (a) {
                var b = a.replace(ca, da);
                return function (a) {
                    return a.getAttribute("id") === b
                }
            }) : (delete d.find.ID, d.filter.ID = function (a) {
                var b = a.replace(ca, da);
                return function (a) {
                    var c = "undefined" != typeof a.getAttributeNode && a.getAttributeNode("id");
                    return c && c.value === b
                }
            }), d.find.TAG = c.getElementsByTagName ? function (a, b) {
                return "undefined" != typeof b.getElementsByTagName ? b.getElementsByTagName(a) : c.qsa ? b.querySelectorAll(a) : void 0
            } : function (a, b) {
                var c, d = [], e = 0, f = b.getElementsByTagName(a);
                if ("*" === a) {
                    while (c = f[e++]) {
                        1 === c.nodeType && d.push(c)
                    }
                    return d
                }
                return f
            }, d.find.CLASS = c.getElementsByClassName && function (a, b) {
                return p ? b.getElementsByClassName(a) : void 0
            }, r = [], q = [], (c.qsa = $.test(g.querySelectorAll)) && (ja(function (a) {
                o.appendChild(a).innerHTML = "<a id='" + u + "'></a><select id='" + u + "-\f]' msallowcapture=''><option selected=''></option></select>", a.querySelectorAll("[msallowcapture^='']").length && q.push("[*^$]=" + L + "*(?:''|\"\")"), a.querySelectorAll("[selected]").length || q.push("\\[" + L + "*(?:value|" + K + ")"), a.querySelectorAll("[id~=" + u + "-]").length || q.push("~="), a.querySelectorAll(":checked").length || q.push(":checked"), a.querySelectorAll("a#" + u + "+*").length || q.push(".#.+[+~]")
            }), ja(function (a) {
                var b = g.createElement("input");
                b.setAttribute("type", "hidden"), a.appendChild(b).setAttribute("name", "D"), a.querySelectorAll("[name=d]").length && q.push("name" + L + "*[*^$|!~]?="), a.querySelectorAll(":enabled").length || q.push(":enabled", ":disabled"), a.querySelectorAll("*,:x"), q.push(",.*:")
            })), (c.matchesSelector = $.test(s = o.matches || o.webkitMatchesSelector || o.mozMatchesSelector || o.oMatchesSelector || o.msMatchesSelector)) && ja(function (a) {
                c.disconnectedMatch = s.call(a, "div"), s.call(a, "[s!='']:x"), r.push("!=", P)
            }), q = q.length && new RegExp(q.join("|")), r = r.length && new RegExp(r.join("|")), b = $.test(o.compareDocumentPosition), t = b || $.test(o.contains) ? function (a, b) {
                var c = 9 === a.nodeType ? a.documentElement : a, d = b && b.parentNode;
                return a === d || !(!d || 1 !== d.nodeType || !(c.contains ? c.contains(d) : a.compareDocumentPosition && 16 & a.compareDocumentPosition(d)))
            } : function (a, b) {
                if (b) {
                    while (b = b.parentNode) {
                        if (b === a) {
                            return !0
                        }
                    }
                }
                return !1
            }, B = b ? function (a, b) {
                if (a === b) {
                    return l = !0, 0
                }
                var d = !a.compareDocumentPosition - !b.compareDocumentPosition;
                return d ? d : (d = (a.ownerDocument || a) === (b.ownerDocument || b) ? a.compareDocumentPosition(b) : 1, 1 & d || !c.sortDetached && b.compareDocumentPosition(a) === d ? a === g || a.ownerDocument === v && t(v, a) ? -1 : b === g || b.ownerDocument === v && t(v, b) ? 1 : k ? J(k, a) - J(k, b) : 0 : 4 & d ? -1 : 1)
            } : function (a, b) {
                if (a === b) {
                    return l = !0, 0
                }
                var c, d = 0, e = a.parentNode, f = b.parentNode, h = [a], i = [b];
                if (!e || !f) {
                    return a === g ? -1 : b === g ? 1 : e ? -1 : f ? 1 : k ? J(k, a) - J(k, b) : 0
                }
                if (e === f) {
                    return la(a, b)
                }
                c = a;
                while (c = c.parentNode) {
                    h.unshift(c)
                }
                c = b;
                while (c = c.parentNode) {
                    i.unshift(c)
                }
                while (h[d] === i[d]) {
                    d++
                }
                return d ? la(h[d], i[d]) : h[d] === v ? -1 : i[d] === v ? 1 : 0
            }, g) : n
        }, ga.matches = function (a, b) {
            return ga(a, null, null, b)
        }, ga.matchesSelector = function (a, b) {
            if ((a.ownerDocument || a) !== n && m(a), b = b.replace(U, "='$1']"), !(!c.matchesSelector || !p || r && r.test(b) || q && q.test(b))) {
                try {
                    var d = s.call(a, b);
                    if (d || c.disconnectedMatch || a.document && 11 !== a.document.nodeType) {
                        return d
                    }
                } catch (e) {
                }
            }
            return ga(b, n, null, [a]).length > 0
        }, ga.contains = function (a, b) {
            return (a.ownerDocument || a) !== n && m(a), t(a, b)
        }, ga.attr = function (a, b) {
            (a.ownerDocument || a) !== n && m(a);
            var e = d.attrHandle[b.toLowerCase()],
                f = e && D.call(d.attrHandle, b.toLowerCase()) ? e(a, b, !p) : void 0;
            return void 0 !== f ? f : c.attributes || !p ? a.getAttribute(b) : (f = a.getAttributeNode(b)) && f.specified ? f.value : null
        }, ga.error = function (a) {
            throw new Error("Syntax error, unrecognized expression: " + a)
        }, ga.uniqueSort = function (a) {
            var b, d = [], e = 0, f = 0;
            if (l = !c.detectDuplicates, k = !c.sortStable && a.slice(0), a.sort(B), l) {
                while (b = a[f++]) {
                    b === a[f] && (e = d.push(f))
                }
                while (e--) {
                    a.splice(d[e], 1)
                }
            }
            return k = null, a
        }, e = ga.getText = function (a) {
            var b, c = "", d = 0, f = a.nodeType;
            if (f) {
                if (1 === f || 9 === f || 11 === f) {
                    if ("string" == typeof a.textContent) {
                        return a.textContent
                    }
                    for (a = a.firstChild; a; a = a.nextSibling) {
                        c += e(a)
                    }
                } else {
                    if (3 === f || 4 === f) {
                        return a.nodeValue
                    }
                }
            } else {
                while (b = a[d++]) {
                    c += e(b)
                }
            }
            return c
        }, d = ga.selectors = {
            cacheLength: 50,
            createPseudo: ia,
            match: X,
            attrHandle: {},
            find: {},
            relative: {
                ">": {dir: "parentNode", first: !0},
                " ": {dir: "parentNode"},
                "+": {dir: "previousSibling", first: !0},
                "~": {dir: "previousSibling"}
            },
            preFilter: {
                ATTR: function (a) {
                    return a[1] = a[1].replace(ca, da), a[3] = (a[3] || a[4] || a[5] || "").replace(ca, da), "~=" === a[2] && (a[3] = " " + a[3] + " "), a.slice(0, 4)
                }, CHILD: function (a) {
                    return a[1] = a[1].toLowerCase(), "nth" === a[1].slice(0, 3) ? (a[3] || ga.error(a[0]), a[4] = +(a[4] ? a[5] + (a[6] || 1) : 2 * ("even" === a[3] || "odd" === a[3])), a[5] = +(a[7] + a[8] || "odd" === a[3])) : a[3] && ga.error(a[0]), a
                }, PSEUDO: function (a) {
                    var b, c = !a[6] && a[2];
                    return X.CHILD.test(a[0]) ? null : (a[3] ? a[2] = a[4] || a[5] || "" : c && V.test(c) && (b = g(c, !0)) && (b = c.indexOf(")", c.length - b) - c.length) && (a[0] = a[0].slice(0, b), a[2] = c.slice(0, b)), a.slice(0, 3))
                }
            },
            filter: {
                TAG: function (a) {
                    var b = a.replace(ca, da).toLowerCase();
                    return "*" === a ? function () {
                        return !0
                    } : function (a) {
                        return a.nodeName && a.nodeName.toLowerCase() === b
                    }
                }, CLASS: function (a) {
                    var b = y[a + " "];
                    return b || (b = new RegExp("(^|" + L + ")" + a + "(" + L + "|$)")) && y(a, function (a) {
                        return b.test("string" == typeof a.className && a.className || "undefined" != typeof a.getAttribute && a.getAttribute("class") || "")
                    })
                }, ATTR: function (a, b, c) {
                    return function (d) {
                        var e = ga.attr(d, a);
                        return null == e ? "!=" === b : b ? (e += "", "=" === b ? e === c : "!=" === b ? e !== c : "^=" === b ? c && 0 === e.indexOf(c) : "*=" === b ? c && e.indexOf(c) > -1 : "$=" === b ? c && e.slice(-c.length) === c : "~=" === b ? (" " + e.replace(Q, " ") + " ").indexOf(c) > -1 : "|=" === b ? e === c || e.slice(0, c.length + 1) === c + "-" : !1) : !0
                    }
                }, CHILD: function (a, b, c, d, e) {
                    var f = "nth" !== a.slice(0, 3), g = "last" !== a.slice(-4), h = "of-type" === b;
                    return 1 === d && 0 === e ? function (a) {
                        return !!a.parentNode
                    } : function (b, c, i) {
                        var j, k, l, m, n, o, p = f !== g ? "nextSibling" : "previousSibling", q = b.parentNode,
                            r = h && b.nodeName.toLowerCase(), s = !i && !h;
                        if (q) {
                            if (f) {
                                while (p) {
                                    l = b;
                                    while (l = l[p]) {
                                        if (h ? l.nodeName.toLowerCase() === r : 1 === l.nodeType) {
                                            return !1
                                        }
                                    }
                                    o = p = "only" === a && !o && "nextSibling"
                                }
                                return !0
                            }
                            if (o = [g ? q.firstChild : q.lastChild], g && s) {
                                k = q[u] || (q[u] = {}), j = k[a] || [], n = j[0] === w && j[1], m = j[0] === w && j[2], l = n && q.childNodes[n];
                                while (l = ++n && l && l[p] || (m = n = 0) || o.pop()) {
                                    if (1 === l.nodeType && ++m && l === b) {
                                        k[a] = [w, n, m];
                                        break
                                    }
                                }
                            } else {
                                if (s && (j = (b[u] || (b[u] = {}))[a]) && j[0] === w) {
                                    m = j[1]
                                } else {
                                    while (l = ++n && l && l[p] || (m = n = 0) || o.pop()) {
                                        if ((h ? l.nodeName.toLowerCase() === r : 1 === l.nodeType) && ++m && (s && ((l[u] || (l[u] = {}))[a] = [w, m]), l === b)) {
                                            break
                                        }
                                    }
                                }
                            }
                            return m -= e, m === d || m % d === 0 && m / d >= 0
                        }
                    }
                }, PSEUDO: function (a, b) {
                    var c, e = d.pseudos[a] || d.setFilters[a.toLowerCase()] || ga.error("unsupported pseudo: " + a);
                    return e[u] ? e(b) : e.length > 1 ? (c = [a, a, "", b], d.setFilters.hasOwnProperty(a.toLowerCase()) ? ia(function (a, c) {
                        var d, f = e(a, b), g = f.length;
                        while (g--) {
                            d = J(a, f[g]), a[d] = !(c[d] = f[g])
                        }
                    }) : function (a) {
                        return e(a, 0, c)
                    }) : e
                }
            },
            pseudos: {
                not: ia(function (a) {
                    var b = [], c = [], d = h(a.replace(R, "$1"));
                    return d[u] ? ia(function (a, b, c, e) {
                        var f, g = d(a, null, e, []), h = a.length;
                        while (h--) {
                            (f = g[h]) && (a[h] = !(b[h] = f))
                        }
                    }) : function (a, e, f) {
                        return b[0] = a, d(b, null, f, c), b[0] = null, !c.pop()
                    }
                }), has: ia(function (a) {
                    return function (b) {
                        return ga(a, b).length > 0
                    }
                }), contains: ia(function (a) {
                    return a = a.replace(ca, da), function (b) {
                        return (b.textContent || b.innerText || e(b)).indexOf(a) > -1
                    }
                }), lang: ia(function (a) {
                    return W.test(a || "") || ga.error("unsupported lang: " + a), a = a.replace(ca, da).toLowerCase(), function (b) {
                        var c;
                        do {
                            if (c = p ? b.lang : b.getAttribute("xml:lang") || b.getAttribute("lang")) {
                                return c = c.toLowerCase(), c === a || 0 === c.indexOf(a + "-")
                            }
                        } while ((b = b.parentNode) && 1 === b.nodeType);
                        return !1
                    }
                }), target: function (b) {
                    var c = a.location && a.location.hash;
                    return c && c.slice(1) === b.id
                }, root: function (a) {
                    return a === o
                }, focus: function (a) {
                    return a === n.activeElement && (!n.hasFocus || n.hasFocus()) && !!(a.type || a.href || ~a.tabIndex)
                }, enabled: function (a) {
                    return a.disabled === !1
                }, disabled: function (a) {
                    return a.disabled === !0
                }, checked: function (a) {
                    var b = a.nodeName.toLowerCase();
                    return "input" === b && !!a.checked || "option" === b && !!a.selected
                }, selected: function (a) {
                    return a.parentNode && a.parentNode.selectedIndex, a.selected === !0
                }, empty: function (a) {
                    for (a = a.firstChild; a; a = a.nextSibling) {
                        if (a.nodeType < 6) {
                            return !1
                        }
                    }
                    return !0
                }, parent: function (a) {
                    return !d.pseudos.empty(a)
                }, header: function (a) {
                    return Z.test(a.nodeName)
                }, input: function (a) {
                    return Y.test(a.nodeName)
                }, button: function (a) {
                    var b = a.nodeName.toLowerCase();
                    return "input" === b && "button" === a.type || "button" === b
                }, text: function (a) {
                    var b;
                    return "input" === a.nodeName.toLowerCase() && "text" === a.type && (null == (b = a.getAttribute("type")) || "text" === b.toLowerCase())
                }, first: oa(function () {
                    return [0]
                }), last: oa(function (a, b) {
                    return [b - 1]
                }), eq: oa(function (a, b, c) {
                    return [0 > c ? c + b : c]
                }), even: oa(function (a, b) {
                    for (var c = 0; b > c; c += 2) {
                        a.push(c)
                    }
                    return a
                }), odd: oa(function (a, b) {
                    for (var c = 1; b > c; c += 2) {
                        a.push(c)
                    }
                    return a
                }), lt: oa(function (a, b, c) {
                    for (var d = 0 > c ? c + b : c; --d >= 0;) {
                        a.push(d)
                    }
                    return a
                }), gt: oa(function (a, b, c) {
                    for (var d = 0 > c ? c + b : c; ++d < b;) {
                        a.push(d)
                    }
                    return a
                })
            }
        }, d.pseudos.nth = d.pseudos.eq;
        for (b in {radio: !0, checkbox: !0, file: !0, password: !0, image: !0}) {
            d.pseudos[b] = ma(b)
        }
        for (b in {submit: !0, reset: !0}) {
            d.pseudos[b] = na(b)
        }

        function qa() {
        }

        qa.prototype = d.filters = d.pseudos, d.setFilters = new qa, g = ga.tokenize = function (a, b) {
            var c, e, f, g, h, i, j, k = z[a + " "];
            if (k) {
                return b ? 0 : k.slice(0)
            }
            h = a, i = [], j = d.preFilter;
            while (h) {
                (!c || (e = S.exec(h))) && (e && (h = h.slice(e[0].length) || h), i.push(f = [])), c = !1, (e = T.exec(h)) && (c = e.shift(), f.push({
                    value: c,
                    type: e[0].replace(R, " ")
                }), h = h.slice(c.length));
                for (g in d.filter) {
                    !(e = X[g].exec(h)) || j[g] && !(e = j[g](e)) || (c = e.shift(), f.push({
                        value: c,
                        type: g,
                        matches: e
                    }), h = h.slice(c.length))
                }
                if (!c) {
                    break
                }
            }
            return b ? h.length : h ? ga.error(a) : z(a, i).slice(0)
        };

        function ra(a) {
            for (var b = 0, c = a.length, d = ""; c > b; b++) {
                d += a[b].value
            }
            return d
        }

        function sa(a, b, c) {
            var d = b.dir, e = c && "parentNode" === d, f = x++;
            return b.first ? function (b, c, f) {
                while (b = b[d]) {
                    if (1 === b.nodeType || e) {
                        return a(b, c, f)
                    }
                }
            } : function (b, c, g) {
                var h, i, j = [w, f];
                if (g) {
                    while (b = b[d]) {
                        if ((1 === b.nodeType || e) && a(b, c, g)) {
                            return !0
                        }
                    }
                } else {
                    while (b = b[d]) {
                        if (1 === b.nodeType || e) {
                            if (i = b[u] || (b[u] = {}), (h = i[d]) && h[0] === w && h[1] === f) {
                                return j[2] = h[2]
                            }
                            if (i[d] = j, j[2] = a(b, c, g)) {
                                return !0
                            }
                        }
                    }
                }
            }
        }

        function ta(a) {
            return a.length > 1 ? function (b, c, d) {
                var e = a.length;
                while (e--) {
                    if (!a[e](b, c, d)) {
                        return !1
                    }
                }
                return !0
            } : a[0]
        }

        function ua(a, b, c) {
            for (var d = 0, e = b.length; e > d; d++) {
                ga(a, b[d], c)
            }
            return c
        }

        function va(a, b, c, d, e) {
            for (var f, g = [], h = 0, i = a.length, j = null != b; i > h; h++) {
                (f = a[h]) && (!c || c(f, d, e)) && (g.push(f), j && b.push(h))
            }
            return g
        }

        function wa(a, b, c, d, e, f) {
            return d && !d[u] && (d = wa(d)), e && !e[u] && (e = wa(e, f)), ia(function (f, g, h, i) {
                var j, k, l, m = [], n = [], o = g.length, p = f || ua(b || "*", h.nodeType ? [h] : h, []),
                    q = !a || !f && b ? p : va(p, m, a, h, i), r = c ? e || (f ? a : o || d) ? [] : g : q;
                if (c && c(q, r, h, i), d) {
                    j = va(r, n), d(j, [], h, i), k = j.length;
                    while (k--) {
                        (l = j[k]) && (r[n[k]] = !(q[n[k]] = l))
                    }
                }
                if (f) {
                    if (e || a) {
                        if (e) {
                            j = [], k = r.length;
                            while (k--) {
                                (l = r[k]) && j.push(q[k] = l)
                            }
                            e(null, r = [], j, i)
                        }
                        k = r.length;
                        while (k--) {
                            (l = r[k]) && (j = e ? J(f, l) : m[k]) > -1 && (f[j] = !(g[j] = l))
                        }
                    }
                } else {
                    r = va(r === g ? r.splice(o, r.length) : r), e ? e(null, g, r, i) : H.apply(g, r)
                }
            })
        }

        function xa(a) {
            for (var b, c, e, f = a.length, g = d.relative[a[0].type], h = g || d.relative[" "], i = g ? 1 : 0, k = sa(function (a) {
                return a === b
            }, h, !0), l = sa(function (a) {
                return J(b, a) > -1
            }, h, !0), m = [function (a, c, d) {
                var e = !g && (d || c !== j) || ((b = c).nodeType ? k(a, c, d) : l(a, c, d));
                return b = null, e
            }]; f > i; i++) {
                if (c = d.relative[a[i].type]) {
                    m = [sa(ta(m), c)]
                } else {
                    if (c = d.filter[a[i].type].apply(null, a[i].matches), c[u]) {
                        for (e = ++i; f > e; e++) {
                            if (d.relative[a[e].type]) {
                                break
                            }
                        }
                        return wa(i > 1 && ta(m), i > 1 && ra(a.slice(0, i - 1).concat({value: " " === a[i - 2].type ? "*" : ""})).replace(R, "$1"), c, e > i && xa(a.slice(i, e)), f > e && xa(a = a.slice(e)), f > e && ra(a))
                    }
                    m.push(c)
                }
            }
            return ta(m)
        }

        function ya(a, b) {
            var c = b.length > 0, e = a.length > 0, f = function (f, g, h, i, k) {
                var l, m, o, p = 0, q = "0", r = f && [], s = [], t = j, u = f || e && d.find.TAG("*", k),
                    v = w += null == t ? 1 : Math.random() || 0.1, x = u.length;
                for (k && (j = g !== n && g); q !== x && null != (l = u[q]); q++) {
                    if (e && l) {
                        m = 0;
                        while (o = a[m++]) {
                            if (o(l, g, h)) {
                                i.push(l);
                                break
                            }
                        }
                        k && (w = v)
                    }
                    c && ((l = !o && l) && p--, f && r.push(l))
                }
                if (p += q, c && q !== p) {
                    m = 0;
                    while (o = b[m++]) {
                        o(r, s, g, h)
                    }
                    if (f) {
                        if (p > 0) {
                            while (q--) {
                                r[q] || s[q] || (s[q] = F.call(i))
                            }
                        }
                        s = va(s)
                    }
                    H.apply(i, s), k && !f && s.length > 0 && p + b.length > 1 && ga.uniqueSort(i)
                }
                return k && (w = v, j = t), r
            };
            return c ? ia(f) : f
        }

        return h = ga.compile = function (a, b) {
            var c, d = [], e = [], f = A[a + " "];
            if (!f) {
                b || (b = g(a)), c = b.length;
                while (c--) {
                    f = xa(b[c]), f[u] ? d.push(f) : e.push(f)
                }
                f = A(a, ya(e, d)), f.selector = a
            }
            return f
        }, i = ga.select = function (a, b, e, f) {
            var i, j, k, l, m, n = "function" == typeof a && a, o = !f && g(a = n.selector || a);
            if (e = e || [], 1 === o.length) {
                if (j = o[0] = o[0].slice(0), j.length > 2 && "ID" === (k = j[0]).type && c.getById && 9 === b.nodeType && p && d.relative[j[1].type]) {
                    if (b = (d.find.ID(k.matches[0].replace(ca, da), b) || [])[0], !b) {
                        return e
                    }
                    n && (b = b.parentNode), a = a.slice(j.shift().value.length)
                }
                i = X.needsContext.test(a) ? 0 : j.length;
                while (i--) {
                    if (k = j[i], d.relative[l = k.type]) {
                        break
                    }
                    if ((m = d.find[l]) && (f = m(k.matches[0].replace(ca, da), aa.test(j[0].type) && pa(b.parentNode) || b))) {
                        if (j.splice(i, 1), a = f.length && ra(j), !a) {
                            return H.apply(e, f), e
                        }
                        break
                    }
                }
            }
            return (n || h(a, o))(f, b, !p, e, aa.test(a) && pa(b.parentNode) || b), e
        }, c.sortStable = u.split("").sort(B).join("") === u, c.detectDuplicates = !!l, m(), c.sortDetached = ja(function (a) {
            return 1 & a.compareDocumentPosition(n.createElement("div"))
        }), ja(function (a) {
            return a.innerHTML = "<a href='#'></a>", "#" === a.firstChild.getAttribute("href")
        }) || ka("type|href|height|width", function (a, b, c) {
            return c ? void 0 : a.getAttribute(b, "type" === b.toLowerCase() ? 1 : 2)
        }), c.attributes && ja(function (a) {
            return a.innerHTML = "<input/>", a.firstChild.setAttribute("value", ""), "" === a.firstChild.getAttribute("value")
        }) || ka("value", function (a, b, c) {
            return c || "input" !== a.nodeName.toLowerCase() ? void 0 : a.defaultValue
        }), ja(function (a) {
            return null == a.getAttribute("disabled")
        }) || ka(K, function (a, b, c) {
            var d;
            return c ? void 0 : a[b] === !0 ? b.toLowerCase() : (d = a.getAttributeNode(b)) && d.specified ? d.value : null
        }), ga
    }(a);
    m.find = s, m.expr = s.selectors, m.expr[":"] = m.expr.pseudos, m.unique = s.uniqueSort, m.text = s.getText, m.isXMLDoc = s.isXML, m.contains = s.contains;
    var t = m.expr.match.needsContext, u = /^<(\w+)\s*\/?>(?:<\/\1>|)$/, v = /^.[^:#\[\.,]*$/;

    function w(a, b, c) {
        if (m.isFunction(b)) {
            return m.grep(a, function (a, d) {
                return !!b.call(a, d, a) !== c
            })
        }
        if (b.nodeType) {
            return m.grep(a, function (a) {
                return a === b !== c
            })
        }
        if ("string" == typeof b) {
            if (v.test(b)) {
                return m.filter(b, a, c)
            }
            b = m.filter(b, a)
        }
        return m.grep(a, function (a) {
            return m.inArray(a, b) >= 0 !== c
        })
    }

    m.filter = function (a, b, c) {
        var d = b[0];
        return c && (a = ":not(" + a + ")"), 1 === b.length && 1 === d.nodeType ? m.find.matchesSelector(d, a) ? [d] : [] : m.find.matches(a, m.grep(b, function (a) {
            return 1 === a.nodeType
        }))
    }, m.fn.extend({
        find: function (a) {
            var b, c = [], d = this, e = d.length;
            if ("string" != typeof a) {
                return this.pushStack(m(a).filter(function () {
                    for (b = 0; e > b; b++) {
                        if (m.contains(d[b], this)) {
                            return !0
                        }
                    }
                }))
            }
            for (b = 0; e > b; b++) {
                m.find(a, d[b], c)
            }
            return c = this.pushStack(e > 1 ? m.unique(c) : c), c.selector = this.selector ? this.selector + " " + a : a, c
        }, filter: function (a) {
            return this.pushStack(w(this, a || [], !1))
        }, not: function (a) {
            return this.pushStack(w(this, a || [], !0))
        }, is: function (a) {
            return !!w(this, "string" == typeof a && t.test(a) ? m(a) : a || [], !1).length
        }
    });
    var x, y = a.document, z = /^(?:\s*(<[\w\W]+>)[^>]*|#([\w-]*))$/, A = m.fn.init = function (a, b) {
        var c, d;
        if (!a) {
            return this
        }
        if ("string" == typeof a) {
            if (c = "<" === a.charAt(0) && ">" === a.charAt(a.length - 1) && a.length >= 3 ? [null, a, null] : z.exec(a), !c || !c[1] && b) {
                return !b || b.jquery ? (b || x).find(a) : this.constructor(b).find(a)
            }
            if (c[1]) {
                if (b = b instanceof m ? b[0] : b, m.merge(this, m.parseHTML(c[1], b && b.nodeType ? b.ownerDocument || b : y, !0)), u.test(c[1]) && m.isPlainObject(b)) {
                    for (c in b) {
                        m.isFunction(this[c]) ? this[c](b[c]) : this.attr(c, b[c])
                    }
                }
                return this
            }
            if (d = y.getElementById(c[2]), d && d.parentNode) {
                if (d.id !== c[2]) {
                    return x.find(a)
                }
                this.length = 1, this[0] = d
            }
            return this.context = y, this.selector = a, this
        }
        return a.nodeType ? (this.context = this[0] = a, this.length = 1, this) : m.isFunction(a) ? "undefined" != typeof x.ready ? x.ready(a) : a(m) : (void 0 !== a.selector && (this.selector = a.selector, this.context = a.context), m.makeArray(a, this))
    };
    A.prototype = m.fn, x = m(y);
    var B = /^(?:parents|prev(?:Until|All))/, C = {children: !0, contents: !0, next: !0, prev: !0};
    m.extend({
        dir: function (a, b, c) {
            var d = [], e = a[b];
            while (e && 9 !== e.nodeType && (void 0 === c || 1 !== e.nodeType || !m(e).is(c))) {
                1 === e.nodeType && d.push(e), e = e[b]
            }
            return d
        }, sibling: function (a, b) {
            for (var c = []; a; a = a.nextSibling) {
                1 === a.nodeType && a !== b && c.push(a)
            }
            return c
        }
    }), m.fn.extend({
        has: function (a) {
            var b, c = m(a, this), d = c.length;
            return this.filter(function () {
                for (b = 0; d > b; b++) {
                    if (m.contains(this, c[b])) {
                        return !0
                    }
                }
            })
        }, closest: function (a, b) {
            for (var c, d = 0, e = this.length, f = [], g = t.test(a) || "string" != typeof a ? m(a, b || this.context) : 0; e > d; d++) {
                for (c = this[d]; c && c !== b; c = c.parentNode) {
                    if (c.nodeType < 11 && (g ? g.index(c) > -1 : 1 === c.nodeType && m.find.matchesSelector(c, a))) {
                        f.push(c);
                        break
                    }
                }
            }
            return this.pushStack(f.length > 1 ? m.unique(f) : f)
        }, index: function (a) {
            return a ? "string" == typeof a ? m.inArray(this[0], m(a)) : m.inArray(a.jquery ? a[0] : a, this) : this[0] && this[0].parentNode ? this.first().prevAll().length : -1
        }, add: function (a, b) {
            return this.pushStack(m.unique(m.merge(this.get(), m(a, b))))
        }, addBack: function (a) {
            return this.add(null == a ? this.prevObject : this.prevObject.filter(a))
        }
    });

    function D(a, b) {
        do {
            a = a[b]
        } while (a && 1 !== a.nodeType);
        return a
    }

    m.each({
        parent: function (a) {
            var b = a.parentNode;
            return b && 11 !== b.nodeType ? b : null
        }, parents: function (a) {
            return m.dir(a, "parentNode")
        }, parentsUntil: function (a, b, c) {
            return m.dir(a, "parentNode", c)
        }, next: function (a) {
            return D(a, "nextSibling")
        }, prev: function (a) {
            return D(a, "previousSibling")
        }, nextAll: function (a) {
            return m.dir(a, "nextSibling")
        }, prevAll: function (a) {
            return m.dir(a, "previousSibling")
        }, nextUntil: function (a, b, c) {
            return m.dir(a, "nextSibling", c)
        }, prevUntil: function (a, b, c) {
            return m.dir(a, "previousSibling", c)
        }, siblings: function (a) {
            return m.sibling((a.parentNode || {}).firstChild, a)
        }, children: function (a) {
            return m.sibling(a.firstChild)
        }, contents: function (a) {
            return m.nodeName(a, "iframe") ? a.contentDocument || a.contentWindow.document : m.merge([], a.childNodes)
        }
    }, function (a, b) {
        m.fn[a] = function (c, d) {
            var e = m.map(this, b, c);
            return "Until" !== a.slice(-5) && (d = c), d && "string" == typeof d && (e = m.filter(d, e)), this.length > 1 && (C[a] || (e = m.unique(e)), B.test(a) && (e = e.reverse())), this.pushStack(e)
        }
    });
    var E = /\S+/g, F = {};

    function G(a) {
        var b = F[a] = {};
        return m.each(a.match(E) || [], function (a, c) {
            b[c] = !0
        }), b
    }

    m.Callbacks = function (a) {
        a = "string" == typeof a ? F[a] || G(a) : m.extend({}, a);
        var b, c, d, e, f, g, h = [], i = !a.once && [], j = function (l) {
            for (c = a.memory && l, d = !0, f = g || 0, g = 0, e = h.length, b = !0; h && e > f; f++) {
                if (h[f].apply(l[0], l[1]) === !1 && a.stopOnFalse) {
                    c = !1;
                    break
                }
            }
            b = !1, h && (i ? i.length && j(i.shift()) : c ? h = [] : k.disable())
        }, k = {
            add: function () {
                if (h) {
                    var d = h.length;
                    !function f(b) {
                        m.each(b, function (b, c) {
                            var d = m.type(c);
                            "function" === d ? a.unique && k.has(c) || h.push(c) : c && c.length && "string" !== d && f(c)
                        })
                    }(arguments), b ? e = h.length : c && (g = d, j(c))
                }
                return this
            }, remove: function () {
                return h && m.each(arguments, function (a, c) {
                    var d;
                    while ((d = m.inArray(c, h, d)) > -1) {
                        h.splice(d, 1), b && (e >= d && e--, f >= d && f--)
                    }
                }), this
            }, has: function (a) {
                return a ? m.inArray(a, h) > -1 : !(!h || !h.length)
            }, empty: function () {
                return h = [], e = 0, this
            }, disable: function () {
                return h = i = c = void 0, this
            }, disabled: function () {
                return !h
            }, lock: function () {
                return i = void 0, c || k.disable(), this
            }, locked: function () {
                return !i
            }, fireWith: function (a, c) {
                return !h || d && !i || (c = c || [], c = [a, c.slice ? c.slice() : c], b ? i.push(c) : j(c)), this
            }, fire: function () {
                return k.fireWith(this, arguments), this
            }, fired: function () {
                return !!d
            }
        };
        return k
    }, m.extend({
        Deferred: function (a) {
            var b = [["resolve", "done", m.Callbacks("once memory"), "resolved"], ["reject", "fail", m.Callbacks("once memory"), "rejected"], ["notify", "progress", m.Callbacks("memory")]],
                c = "pending", d = {
                    state: function () {
                        return c
                    }, always: function () {
                        return e.done(arguments).fail(arguments), this
                    }, then: function () {
                        var a = arguments;
                        return m.Deferred(function (c) {
                            m.each(b, function (b, f) {
                                var g = m.isFunction(a[b]) && a[b];
                                e[f[1]](function () {
                                    var a = g && g.apply(this, arguments);
                                    a && m.isFunction(a.promise) ? a.promise().done(c.resolve).fail(c.reject).progress(c.notify) : c[f[0] + "With"](this === d ? c.promise() : this, g ? [a] : arguments)
                                })
                            }), a = null
                        }).promise()
                    }, promise: function (a) {
                        return null != a ? m.extend(a, d) : d
                    }
                }, e = {};
            return d.pipe = d.then, m.each(b, function (a, f) {
                var g = f[2], h = f[3];
                d[f[1]] = g.add, h && g.add(function () {
                    c = h
                }, b[1 ^ a][2].disable, b[2][2].lock), e[f[0]] = function () {
                    return e[f[0] + "With"](this === e ? d : this, arguments), this
                }, e[f[0] + "With"] = g.fireWith
            }), d.promise(e), a && a.call(e, e), e
        }, when: function (a) {
            var b = 0, c = d.call(arguments), e = c.length, f = 1 !== e || a && m.isFunction(a.promise) ? e : 0,
                g = 1 === f ? a : m.Deferred(), h = function (a, b, c) {
                    return function (e) {
                        b[a] = this, c[a] = arguments.length > 1 ? d.call(arguments) : e, c === i ? g.notifyWith(b, c) : --f || g.resolveWith(b, c)
                    }
                }, i, j, k;
            if (e > 1) {
                for (i = new Array(e), j = new Array(e), k = new Array(e); e > b; b++) {
                    c[b] && m.isFunction(c[b].promise) ? c[b].promise().done(h(b, k, c)).fail(g.reject).progress(h(b, j, i)) : --f
                }
            }
            return f || g.resolveWith(k, c), g.promise()
        }
    });
    var H;
    m.fn.ready = function (a) {
        return m.ready.promise().done(a), this
    }, m.extend({
        isReady: !1, readyWait: 1, holdReady: function (a) {
            a ? m.readyWait++ : m.ready(!0)
        }, ready: function (a) {
            if (a === !0 ? !--m.readyWait : !m.isReady) {
                if (!y.body) {
                    return setTimeout(m.ready)
                }
                m.isReady = !0, a !== !0 && --m.readyWait > 0 || (H.resolveWith(y, [m]), m.fn.triggerHandler && (m(y).triggerHandler("ready"), m(y).off("ready")))
            }
        }
    });

    function I() {
        y.addEventListener ? (y.removeEventListener("DOMContentLoaded", J, !1), a.removeEventListener("load", J, !1)) : (y.detachEvent("onreadystatechange", J), a.detachEvent("onload", J))
    }

    function J() {
        (y.addEventListener || "load" === event.type || "complete" === y.readyState) && (I(), m.ready())
    }

    m.ready.promise = function (b) {
        if (!H) {
            if (H = m.Deferred(), "complete" === y.readyState) {
                setTimeout(m.ready)
            } else {
                if (y.addEventListener) {
                    y.addEventListener("DOMContentLoaded", J, !1), a.addEventListener("load", J, !1)
                } else {
                    y.attachEvent("onreadystatechange", J), a.attachEvent("onload", J);
                    var c = !1;
                    try {
                        c = null == a.frameElement && y.documentElement
                    } catch (d) {
                    }
                    c && c.doScroll && !function e() {
                        if (!m.isReady) {
                            try {
                                c.doScroll("left")
                            } catch (a) {
                                return setTimeout(e, 50)
                            }
                            I(), m.ready()
                        }
                    }()
                }
            }
        }
        return H.promise(b)
    };
    var K = "undefined", L;
    for (L in m(k)) {
        break
    }
    k.ownLast = "0" !== L, k.inlineBlockNeedsLayout = !1, m(function () {
        var a, b, c, d;
        c = y.getElementsByTagName("body")[0], c && c.style && (b = y.createElement("div"), d = y.createElement("div"), d.style.cssText = "position:absolute;border:0;width:0;height:0;top:0;left:-9999px", c.appendChild(d).appendChild(b), typeof b.style.zoom !== K && (b.style.cssText = "display:inline;margin:0;border:0;padding:1px;width:1px;zoom:1", k.inlineBlockNeedsLayout = a = 3 === b.offsetWidth, a && (c.style.zoom = 1)), c.removeChild(d))
    }), function () {
        var a = y.createElement("div");
        if (null == k.deleteExpando) {
            k.deleteExpando = !0;
            try {
                delete a.test
            } catch (b) {
                k.deleteExpando = !1
            }
        }
        a = null
    }(), m.acceptData = function (a) {
        var b = m.noData[(a.nodeName + " ").toLowerCase()], c = +a.nodeType || 1;
        return 1 !== c && 9 !== c ? !1 : !b || b !== !0 && a.getAttribute("classid") === b
    };
    var M = /^(?:\{[\w\W]*\}|\[[\w\W]*\])$/, N = /([A-Z])/g;

    function O(a, b, c) {
        if (void 0 === c && 1 === a.nodeType) {
            var d = "data-" + b.replace(N, "-$1").toLowerCase();
            if (c = a.getAttribute(d), "string" == typeof c) {
                try {
                    c = "true" === c ? !0 : "false" === c ? !1 : "null" === c ? null : +c + "" === c ? +c : M.test(c) ? m.parseJSON(c) : c
                } catch (e) {
                }
                m.data(a, b, c)
            } else {
                c = void 0
            }
        }
        return c
    }

    function P(a) {
        var b;
        for (b in a) {
            if (("data" !== b || !m.isEmptyObject(a[b])) && "toJSON" !== b) {
                return !1
            }
        }
        return !0
    }

    function Q(a, b, d, e) {
        if (m.acceptData(a)) {
            var f, g, h = m.expando, i = a.nodeType, j = i ? m.cache : a, k = i ? a[h] : a[h] && h;
            if (k && j[k] && (e || j[k].data) || void 0 !== d || "string" != typeof b) {
                return k || (k = i ? a[h] = c.pop() || m.guid++ : h), j[k] || (j[k] = i ? {} : {toJSON: m.noop}), ("object" == typeof b || "function" == typeof b) && (e ? j[k] = m.extend(j[k], b) : j[k].data = m.extend(j[k].data, b)), g = j[k], e || (g.data || (g.data = {}), g = g.data), void 0 !== d && (g[m.camelCase(b)] = d), "string" == typeof b ? (f = g[b], null == f && (f = g[m.camelCase(b)])) : f = g, f
            }
        }
    }

    function R(a, b, c) {
        if (m.acceptData(a)) {
            var d, e, f = a.nodeType, g = f ? m.cache : a, h = f ? a[m.expando] : m.expando;
            if (g[h]) {
                if (b && (d = c ? g[h] : g[h].data)) {
                    m.isArray(b) ? b = b.concat(m.map(b, m.camelCase)) : b in d ? b = [b] : (b = m.camelCase(b), b = b in d ? [b] : b.split(" ")), e = b.length;
                    while (e--) {
                        delete d[b[e]]
                    }
                    if (c ? !P(d) : !m.isEmptyObject(d)) {
                        return
                    }
                }
                (c || (delete g[h].data, P(g[h]))) && (f ? m.cleanData([a], !0) : k.deleteExpando || g != g.window ? delete g[h] : g[h] = null)
            }
        }
    }

    m.extend({
        cache: {},
        noData: {"applet ": !0, "embed ": !0, "object ": "clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"},
        hasData: function (a) {
            return a = a.nodeType ? m.cache[a[m.expando]] : a[m.expando], !!a && !P(a)
        },
        data: function (a, b, c) {
            return Q(a, b, c)
        },
        removeData: function (a, b) {
            return R(a, b)
        },
        _data: function (a, b, c) {
            return Q(a, b, c, !0)
        },
        _removeData: function (a, b) {
            return R(a, b, !0)
        }
    }), m.fn.extend({
        data: function (a, b) {
            var c, d, e, f = this[0], g = f && f.attributes;
            if (void 0 === a) {
                if (this.length && (e = m.data(f), 1 === f.nodeType && !m._data(f, "parsedAttrs"))) {
                    c = g.length;
                    while (c--) {
                        g[c] && (d = g[c].name, 0 === d.indexOf("data-") && (d = m.camelCase(d.slice(5)), O(f, d, e[d])))
                    }
                    m._data(f, "parsedAttrs", !0)
                }
                return e
            }
            return "object" == typeof a ? this.each(function () {
                m.data(this, a)
            }) : arguments.length > 1 ? this.each(function () {
                m.data(this, a, b)
            }) : f ? O(f, a, m.data(f, a)) : void 0
        }, removeData: function (a) {
            return this.each(function () {
                m.removeData(this, a)
            })
        }
    }), m.extend({
        queue: function (a, b, c) {
            var d;
            return a ? (b = (b || "fx") + "queue", d = m._data(a, b), c && (!d || m.isArray(c) ? d = m._data(a, b, m.makeArray(c)) : d.push(c)), d || []) : void 0
        }, dequeue: function (a, b) {
            b = b || "fx";
            var c = m.queue(a, b), d = c.length, e = c.shift(), f = m._queueHooks(a, b), g = function () {
                m.dequeue(a, b)
            };
            "inprogress" === e && (e = c.shift(), d--), e && ("fx" === b && c.unshift("inprogress"), delete f.stop, e.call(a, g, f)), !d && f && f.empty.fire()
        }, _queueHooks: function (a, b) {
            var c = b + "queueHooks";
            return m._data(a, c) || m._data(a, c, {
                empty: m.Callbacks("once memory").add(function () {
                    m._removeData(a, b + "queue"), m._removeData(a, c)
                })
            })
        }
    }), m.fn.extend({
        queue: function (a, b) {
            var c = 2;
            return "string" != typeof a && (b = a, a = "fx", c--), arguments.length < c ? m.queue(this[0], a) : void 0 === b ? this : this.each(function () {
                var c = m.queue(this, a, b);
                m._queueHooks(this, a), "fx" === a && "inprogress" !== c[0] && m.dequeue(this, a)
            })
        }, dequeue: function (a) {
            return this.each(function () {
                m.dequeue(this, a)
            })
        }, clearQueue: function (a) {
            return this.queue(a || "fx", [])
        }, promise: function (a, b) {
            var c, d = 1, e = m.Deferred(), f = this, g = this.length, h = function () {
                --d || e.resolveWith(f, [f])
            };
            "string" != typeof a && (b = a, a = void 0), a = a || "fx";
            while (g--) {
                c = m._data(f[g], a + "queueHooks"), c && c.empty && (d++, c.empty.add(h))
            }
            return h(), e.promise(b)
        }
    });
    var S = /[+-]?(?:\d*\.|)\d+(?:[eE][+-]?\d+|)/.source, T = ["Top", "Right", "Bottom", "Left"], U = function (a, b) {
        return a = b || a, "none" === m.css(a, "display") || !m.contains(a.ownerDocument, a)
    }, V = m.access = function (a, b, c, d, e, f, g) {
        var h = 0, i = a.length, j = null == c;
        if ("object" === m.type(c)) {
            e = !0;
            for (h in c) {
                m.access(a, b, h, c[h], !0, f, g)
            }
        } else {
            if (void 0 !== d && (e = !0, m.isFunction(d) || (g = !0), j && (g ? (b.call(a, d), b = null) : (j = b, b = function (a, b, c) {
                return j.call(m(a), c)
            })), b)) {
                for (; i > h; h++) {
                    b(a[h], c, g ? d : d.call(a[h], h, b(a[h], c)))
                }
            }
        }
        return e ? a : j ? b.call(a) : i ? b(a[0], c) : f
    }, W = /^(?:checkbox|radio)$/i;
    !function () {
        var a = y.createElement("input"), b = y.createElement("div"), c = y.createDocumentFragment();
        if (b.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>", k.leadingWhitespace = 3 === b.firstChild.nodeType, k.tbody = !b.getElementsByTagName("tbody").length, k.htmlSerialize = !!b.getElementsByTagName("link").length, k.html5Clone = "<:nav></:nav>" !== y.createElement("nav").cloneNode(!0).outerHTML, a.type = "checkbox", a.checked = !0, c.appendChild(a), k.appendChecked = a.checked, b.innerHTML = "<textarea>x</textarea>", k.noCloneChecked = !!b.cloneNode(!0).lastChild.defaultValue, c.appendChild(b), b.innerHTML = "<input type='radio' checked='checked' name='t'/>", k.checkClone = b.cloneNode(!0).cloneNode(!0).lastChild.checked, k.noCloneEvent = !0, b.attachEvent && (b.attachEvent("onclick", function () {
            k.noCloneEvent = !1
        }), b.cloneNode(!0).click()), null == k.deleteExpando) {
            k.deleteExpando = !0;
            try {
                delete b.test
            } catch (d) {
                k.deleteExpando = !1
            }
        }
    }(), function () {
        var b, c, d = y.createElement("div");
        for (b in {submit: !0, change: !0, focusin: !0}) {
            c = "on" + b, (k[b + "Bubbles"] = c in a) || (d.setAttribute(c, "t"), k[b + "Bubbles"] = d.attributes[c].expando === !1)
        }
        d = null
    }();
    var X = /^(?:input|select|textarea)$/i, Y = /^key/, Z = /^(?:mouse|pointer|contextmenu)|click/,
        $ = /^(?:focusinfocus|focusoutblur)$/, _ = /^([^.]*)(?:\.(.+)|)$/;

    function aa() {
        return !0
    }

    function ba() {
        return !1
    }

    function ca() {
        try {
            return y.activeElement
        } catch (a) {
        }
    }

    m.event = {
        global: {},
        add: function (a, b, c, d, e) {
            var f, g, h, i, j, k, l, n, o, p, q, r = m._data(a);
            if (r) {
                c.handler && (i = c, c = i.handler, e = i.selector), c.guid || (c.guid = m.guid++), (g = r.events) || (g = r.events = {}), (k = r.handle) || (k = r.handle = function (a) {
                    return typeof m === K || a && m.event.triggered === a.type ? void 0 : m.event.dispatch.apply(k.elem, arguments)
                }, k.elem = a), b = (b || "").match(E) || [""], h = b.length;
                while (h--) {
                    f = _.exec(b[h]) || [], o = q = f[1], p = (f[2] || "").split(".").sort(), o && (j = m.event.special[o] || {}, o = (e ? j.delegateType : j.bindType) || o, j = m.event.special[o] || {}, l = m.extend({
                        type: o,
                        origType: q,
                        data: d,
                        handler: c,
                        guid: c.guid,
                        selector: e,
                        needsContext: e && m.expr.match.needsContext.test(e),
                        namespace: p.join(".")
                    }, i), (n = g[o]) || (n = g[o] = [], n.delegateCount = 0, j.setup && j.setup.call(a, d, p, k) !== !1 || (a.addEventListener ? a.addEventListener(o, k, !1) : a.attachEvent && a.attachEvent("on" + o, k))), j.add && (j.add.call(a, l), l.handler.guid || (l.handler.guid = c.guid)), e ? n.splice(n.delegateCount++, 0, l) : n.push(l), m.event.global[o] = !0)
                }
                a = null
            }
        },
        remove: function (a, b, c, d, e) {
            var f, g, h, i, j, k, l, n, o, p, q, r = m.hasData(a) && m._data(a);
            if (r && (k = r.events)) {
                b = (b || "").match(E) || [""], j = b.length;
                while (j--) {
                    if (h = _.exec(b[j]) || [], o = q = h[1], p = (h[2] || "").split(".").sort(), o) {
                        l = m.event.special[o] || {}, o = (d ? l.delegateType : l.bindType) || o, n = k[o] || [], h = h[2] && new RegExp("(^|\\.)" + p.join("\\.(?:.*\\.|)") + "(\\.|$)"), i = f = n.length;
                        while (f--) {
                            g = n[f], !e && q !== g.origType || c && c.guid !== g.guid || h && !h.test(g.namespace) || d && d !== g.selector && ("**" !== d || !g.selector) || (n.splice(f, 1), g.selector && n.delegateCount--, l.remove && l.remove.call(a, g))
                        }
                        i && !n.length && (l.teardown && l.teardown.call(a, p, r.handle) !== !1 || m.removeEvent(a, o, r.handle), delete k[o])
                    } else {
                        for (o in k) {
                            m.event.remove(a, o + b[j], c, d, !0)
                        }
                    }
                }
                m.isEmptyObject(k) && (delete r.handle, m._removeData(a, "events"))
            }
        },
        trigger: function (b, c, d, e) {
            var f, g, h, i, k, l, n, o = [d || y], p = j.call(b, "type") ? b.type : b,
                q = j.call(b, "namespace") ? b.namespace.split(".") : [];
            if (h = l = d = d || y, 3 !== d.nodeType && 8 !== d.nodeType && !$.test(p + m.event.triggered) && (p.indexOf(".") >= 0 && (q = p.split("."), p = q.shift(), q.sort()), g = p.indexOf(":") < 0 && "on" + p, b = b[m.expando] ? b : new m.Event(p, "object" == typeof b && b), b.isTrigger = e ? 2 : 3, b.namespace = q.join("."), b.namespace_re = b.namespace ? new RegExp("(^|\\.)" + q.join("\\.(?:.*\\.|)") + "(\\.|$)") : null, b.result = void 0, b.target || (b.target = d), c = null == c ? [b] : m.makeArray(c, [b]), k = m.event.special[p] || {}, e || !k.trigger || k.trigger.apply(d, c) !== !1)) {
                if (!e && !k.noBubble && !m.isWindow(d)) {
                    for (i = k.delegateType || p, $.test(i + p) || (h = h.parentNode); h; h = h.parentNode) {
                        o.push(h), l = h
                    }
                    l === (d.ownerDocument || y) && o.push(l.defaultView || l.parentWindow || a)
                }
                n = 0;
                while ((h = o[n++]) && !b.isPropagationStopped()) {
                    b.type = n > 1 ? i : k.bindType || p, f = (m._data(h, "events") || {})[b.type] && m._data(h, "handle"), f && f.apply(h, c), f = g && h[g], f && f.apply && m.acceptData(h) && (b.result = f.apply(h, c), b.result === !1 && b.preventDefault())
                }
                if (b.type = p, !e && !b.isDefaultPrevented() && (!k._default || k._default.apply(o.pop(), c) === !1) && m.acceptData(d) && g && d[p] && !m.isWindow(d)) {
                    l = d[g], l && (d[g] = null), m.event.triggered = p;
                    try {
                        d[p]()
                    } catch (r) {
                    }
                    m.event.triggered = void 0, l && (d[g] = l)
                }
                return b.result
            }
        },
        dispatch: function (a) {
            a = m.event.fix(a);
            var b, c, e, f, g, h = [], i = d.call(arguments), j = (m._data(this, "events") || {})[a.type] || [],
                k = m.event.special[a.type] || {};
            if (i[0] = a, a.delegateTarget = this, !k.preDispatch || k.preDispatch.call(this, a) !== !1) {
                h = m.event.handlers.call(this, a, j), b = 0;
                while ((f = h[b++]) && !a.isPropagationStopped()) {
                    a.currentTarget = f.elem, g = 0;
                    while ((e = f.handlers[g++]) && !a.isImmediatePropagationStopped()) {
                        (!a.namespace_re || a.namespace_re.test(e.namespace)) && (a.handleObj = e, a.data = e.data, c = ((m.event.special[e.origType] || {}).handle || e.handler).apply(f.elem, i), void 0 !== c && (a.result = c) === !1 && (a.preventDefault(), a.stopPropagation()))
                    }
                }
                return k.postDispatch && k.postDispatch.call(this, a), a.result
            }
        },
        handlers: function (a, b) {
            var c, d, e, f, g = [], h = b.delegateCount, i = a.target;
            if (h && i.nodeType && (!a.button || "click" !== a.type)) {
                for (; i != this; i = i.parentNode || this) {
                    if (1 === i.nodeType && (i.disabled !== !0 || "click" !== a.type)) {
                        for (e = [], f = 0; h > f; f++) {
                            d = b[f], c = d.selector + " ", void 0 === e[c] && (e[c] = d.needsContext ? m(c, this).index(i) >= 0 : m.find(c, this, null, [i]).length), e[c] && e.push(d)
                        }
                        e.length && g.push({elem: i, handlers: e})
                    }
                }
            }
            return h < b.length && g.push({elem: this, handlers: b.slice(h)}), g
        },
        fix: function (a) {
            if (a[m.expando]) {
                return a
            }
            var b, c, d, e = a.type, f = a, g = this.fixHooks[e];
            g || (this.fixHooks[e] = g = Z.test(e) ? this.mouseHooks : Y.test(e) ? this.keyHooks : {}), d = g.props ? this.props.concat(g.props) : this.props, a = new m.Event(f), b = d.length;
            while (b--) {
                c = d[b], a[c] = f[c]
            }
            return a.target || (a.target = f.srcElement || y), 3 === a.target.nodeType && (a.target = a.target.parentNode), a.metaKey = !!a.metaKey, g.filter ? g.filter(a, f) : a
        },
        props: "altKey bubbles cancelable ctrlKey currentTarget eventPhase metaKey relatedTarget shiftKey target timeStamp view which".split(" "),
        fixHooks: {},
        keyHooks: {
            props: "char charCode key keyCode".split(" "), filter: function (a, b) {
                return null == a.which && (a.which = null != b.charCode ? b.charCode : b.keyCode), a
            }
        },
        mouseHooks: {
            props: "button buttons clientX clientY fromElement offsetX offsetY pageX pageY screenX screenY toElement".split(" "),
            filter: function (a, b) {
                var c, d, e, f = b.button, g = b.fromElement;
                return null == a.pageX && null != b.clientX && (d = a.target.ownerDocument || y, e = d.documentElement, c = d.body, a.pageX = b.clientX + (e && e.scrollLeft || c && c.scrollLeft || 0) - (e && e.clientLeft || c && c.clientLeft || 0), a.pageY = b.clientY + (e && e.scrollTop || c && c.scrollTop || 0) - (e && e.clientTop || c && c.clientTop || 0)), !a.relatedTarget && g && (a.relatedTarget = g === a.target ? b.toElement : g), a.which || void 0 === f || (a.which = 1 & f ? 1 : 2 & f ? 3 : 4 & f ? 2 : 0), a
            }
        },
        special: {
            load: {noBubble: !0}, focus: {
                trigger: function () {
                    if (this !== ca() && this.focus) {
                        try {
                            return this.focus(), !1
                        } catch (a) {
                        }
                    }
                }, delegateType: "focusin"
            }, blur: {
                trigger: function () {
                    return this === ca() && this.blur ? (this.blur(), !1) : void 0
                }, delegateType: "focusout"
            }, click: {
                trigger: function () {
                    return m.nodeName(this, "input") && "checkbox" === this.type && this.click ? (this.click(), !1) : void 0
                }, _default: function (a) {
                    return m.nodeName(a.target, "a")
                }
            }, beforeunload: {
                postDispatch: function (a) {
                    void 0 !== a.result && a.originalEvent && (a.originalEvent.returnValue = a.result)
                }
            }
        },
        simulate: function (a, b, c, d) {
            var e = m.extend(new m.Event, c, {type: a, isSimulated: !0, originalEvent: {}});
            d ? m.event.trigger(e, null, b) : m.event.dispatch.call(b, e), e.isDefaultPrevented() && c.preventDefault()
        }
    }, m.removeEvent = y.removeEventListener ? function (a, b, c) {
        a.removeEventListener && a.removeEventListener(b, c, !1)
    } : function (a, b, c) {
        var d = "on" + b;
        a.detachEvent && (typeof a[d] === K && (a[d] = null), a.detachEvent(d, c))
    }, m.Event = function (a, b) {
        return this instanceof m.Event ? (a && a.type ? (this.originalEvent = a, this.type = a.type, this.isDefaultPrevented = a.defaultPrevented || void 0 === a.defaultPrevented && a.returnValue === !1 ? aa : ba) : this.type = a, b && m.extend(this, b), this.timeStamp = a && a.timeStamp || m.now(), void (this[m.expando] = !0)) : new m.Event(a, b)
    }, m.Event.prototype = {
        isDefaultPrevented: ba,
        isPropagationStopped: ba,
        isImmediatePropagationStopped: ba,
        preventDefault: function () {
            var a = this.originalEvent;
            this.isDefaultPrevented = aa, a && (a.preventDefault ? a.preventDefault() : a.returnValue = !1)
        },
        stopPropagation: function () {
            var a = this.originalEvent;
            this.isPropagationStopped = aa, a && (a.stopPropagation && a.stopPropagation(), a.cancelBubble = !0)
        },
        stopImmediatePropagation: function () {
            var a = this.originalEvent;
            this.isImmediatePropagationStopped = aa, a && a.stopImmediatePropagation && a.stopImmediatePropagation(), this.stopPropagation()
        }
    }, m.each({
        mouseenter: "mouseover",
        mouseleave: "mouseout",
        pointerenter: "pointerover",
        pointerleave: "pointerout"
    }, function (a, b) {
        m.event.special[a] = {
            delegateType: b, bindType: b, handle: function (a) {
                var c, d = this, e = a.relatedTarget, f = a.handleObj;
                return (!e || e !== d && !m.contains(d, e)) && (a.type = f.origType, c = f.handler.apply(this, arguments), a.type = b), c
            }
        }
    }), k.submitBubbles || (m.event.special.submit = {
        setup: function () {
            return m.nodeName(this, "form") ? !1 : void m.event.add(this, "click._submit keypress._submit", function (a) {
                var b = a.target, c = m.nodeName(b, "input") || m.nodeName(b, "button") ? b.form : void 0;
                c && !m._data(c, "submitBubbles") && (m.event.add(c, "submit._submit", function (a) {
                    a._submit_bubble = !0
                }), m._data(c, "submitBubbles", !0))
            })
        }, postDispatch: function (a) {
            a._submit_bubble && (delete a._submit_bubble, this.parentNode && !a.isTrigger && m.event.simulate("submit", this.parentNode, a, !0))
        }, teardown: function () {
            return m.nodeName(this, "form") ? !1 : void m.event.remove(this, "._submit")
        }
    }), k.changeBubbles || (m.event.special.change = {
        setup: function () {
            return X.test(this.nodeName) ? (("checkbox" === this.type || "radio" === this.type) && (m.event.add(this, "propertychange._change", function (a) {
                "checked" === a.originalEvent.propertyName && (this._just_changed = !0)
            }), m.event.add(this, "click._change", function (a) {
                this._just_changed && !a.isTrigger && (this._just_changed = !1), m.event.simulate("change", this, a, !0)
            })), !1) : void m.event.add(this, "beforeactivate._change", function (a) {
                var b = a.target;
                X.test(b.nodeName) && !m._data(b, "changeBubbles") && (m.event.add(b, "change._change", function (a) {
                    !this.parentNode || a.isSimulated || a.isTrigger || m.event.simulate("change", this.parentNode, a, !0)
                }), m._data(b, "changeBubbles", !0))
            })
        }, handle: function (a) {
            var b = a.target;
            return this !== b || a.isSimulated || a.isTrigger || "radio" !== b.type && "checkbox" !== b.type ? a.handleObj.handler.apply(this, arguments) : void 0
        }, teardown: function () {
            return m.event.remove(this, "._change"), !X.test(this.nodeName)
        }
    }), k.focusinBubbles || m.each({focus: "focusin", blur: "focusout"}, function (a, b) {
        var c = function (a) {
            m.event.simulate(b, a.target, m.event.fix(a), !0)
        };
        m.event.special[b] = {
            setup: function () {
                var d = this.ownerDocument || this, e = m._data(d, b);
                e || d.addEventListener(a, c, !0), m._data(d, b, (e || 0) + 1)
            }, teardown: function () {
                var d = this.ownerDocument || this, e = m._data(d, b) - 1;
                e ? m._data(d, b, e) : (d.removeEventListener(a, c, !0), m._removeData(d, b))
            }
        }
    }), m.fn.extend({
        on: function (a, b, c, d, e) {
            var f, g;
            if ("object" == typeof a) {
                "string" != typeof b && (c = c || b, b = void 0);
                for (f in a) {
                    this.on(f, b, c, a[f], e)
                }
                return this
            }
            if (null == c && null == d ? (d = b, c = b = void 0) : null == d && ("string" == typeof b ? (d = c, c = void 0) : (d = c, c = b, b = void 0)), d === !1) {
                d = ba
            } else {
                if (!d) {
                    return this
                }
            }
            return 1 === e && (g = d, d = function (a) {
                return m().off(a), g.apply(this, arguments)
            }, d.guid = g.guid || (g.guid = m.guid++)), this.each(function () {
                m.event.add(this, a, d, c, b)
            })
        }, one: function (a, b, c, d) {
            return this.on(a, b, c, d, 1)
        }, off: function (a, b, c) {
            var d, e;
            if (a && a.preventDefault && a.handleObj) {
                return d = a.handleObj, m(a.delegateTarget).off(d.namespace ? d.origType + "." + d.namespace : d.origType, d.selector, d.handler), this
            }
            if ("object" == typeof a) {
                for (e in a) {
                    this.off(e, b, a[e])
                }
                return this
            }
            return (b === !1 || "function" == typeof b) && (c = b, b = void 0), c === !1 && (c = ba), this.each(function () {
                m.event.remove(this, a, c, b)
            })
        }, trigger: function (a, b) {
            return this.each(function () {
                m.event.trigger(a, b, this)
            })
        }, triggerHandler: function (a, b) {
            var c = this[0];
            return c ? m.event.trigger(a, b, c, !0) : void 0
        }
    });

    function da(a) {
        var b = ea.split("|"), c = a.createDocumentFragment();
        if (c.createElement) {
            while (b.length) {
                c.createElement(b.pop())
            }
        }
        return c
    }

    var ea = "abbr|article|aside|audio|bdi|canvas|data|datalist|details|figcaption|figure|footer|header|hgroup|mark|meter|nav|output|progress|section|summary|time|video",
        fa = / jQuery\d+="(?:null|\d+)"/g, ga = new RegExp("<(?:" + ea + ")[\\s/>]", "i"), ha = /^\s+/,
        ia = /<(?!area|br|col|embed|hr|img|input|link|meta|param)(([\w:]+)[^>]*)\/>/gi, ja = /<([\w:]+)/,
        ka = /<tbody/i, la = /<|&#?\w+;/, ma = /<(?:script|style|link)/i, na = /checked\s*(?:[^=]|=\s*.checked.)/i,
        oa = /^$|\/(?:java|ecma)script/i, pa = /^true\/(.*)/, qa = /^\s*<!(?:\[CDATA\[|--)|(?:\]\]|--)>\s*$/g, ra = {
            option: [1, "<select multiple='multiple'>", "</select>"],
            legend: [1, "<fieldset>", "</fieldset>"],
            area: [1, "<map>", "</map>"],
            param: [1, "<object>", "</object>"],
            thead: [1, "<table>", "</table>"],
            tr: [2, "<table><tbody>", "</tbody></table>"],
            col: [2, "<table><tbody></tbody><colgroup>", "</colgroup></table>"],
            td: [3, "<table><tbody><tr>", "</tr></tbody></table>"],
            _default: k.htmlSerialize ? [0, "", ""] : [1, "X<div>", "</div>"]
        }, sa = da(y), ta = sa.appendChild(y.createElement("div"));
    ra.optgroup = ra.option, ra.tbody = ra.tfoot = ra.colgroup = ra.caption = ra.thead, ra.th = ra.td;

    function ua(a, b) {
        var c, d, e = 0,
            f = typeof a.getElementsByTagName !== K ? a.getElementsByTagName(b || "*") : typeof a.querySelectorAll !== K ? a.querySelectorAll(b || "*") : void 0;
        if (!f) {
            for (f = [], c = a.childNodes || a; null != (d = c[e]); e++) {
                !b || m.nodeName(d, b) ? f.push(d) : m.merge(f, ua(d, b))
            }
        }
        return void 0 === b || b && m.nodeName(a, b) ? m.merge([a], f) : f
    }

    function va(a) {
        W.test(a.type) && (a.defaultChecked = a.checked)
    }

    function wa(a, b) {
        return m.nodeName(a, "table") && m.nodeName(11 !== b.nodeType ? b : b.firstChild, "tr") ? a.getElementsByTagName("tbody")[0] || a.appendChild(a.ownerDocument.createElement("tbody")) : a
    }

    function xa(a) {
        return a.type = (null !== m.find.attr(a, "type")) + "/" + a.type, a
    }

    function ya(a) {
        var b = pa.exec(a.type);
        return b ? a.type = b[1] : a.removeAttribute("type"), a
    }

    function za(a, b) {
        for (var c, d = 0; null != (c = a[d]); d++) {
            m._data(c, "globalEval", !b || m._data(b[d], "globalEval"))
        }
    }

    function Aa(a, b) {
        if (1 === b.nodeType && m.hasData(a)) {
            var c, d, e, f = m._data(a), g = m._data(b, f), h = f.events;
            if (h) {
                delete g.handle, g.events = {};
                for (c in h) {
                    for (d = 0, e = h[c].length; e > d; d++) {
                        m.event.add(b, c, h[c][d])
                    }
                }
            }
            g.data && (g.data = m.extend({}, g.data))
        }
    }

    function Ba(a, b) {
        var c, d, e;
        if (1 === b.nodeType) {
            if (c = b.nodeName.toLowerCase(), !k.noCloneEvent && b[m.expando]) {
                e = m._data(b);
                for (d in e.events) {
                    m.removeEvent(b, d, e.handle)
                }
                b.removeAttribute(m.expando)
            }
            "script" === c && b.text !== a.text ? (xa(b).text = a.text, ya(b)) : "object" === c ? (b.parentNode && (b.outerHTML = a.outerHTML), k.html5Clone && a.innerHTML && !m.trim(b.innerHTML) && (b.innerHTML = a.innerHTML)) : "input" === c && W.test(a.type) ? (b.defaultChecked = b.checked = a.checked, b.value !== a.value && (b.value = a.value)) : "option" === c ? b.defaultSelected = b.selected = a.defaultSelected : ("input" === c || "textarea" === c) && (b.defaultValue = a.defaultValue)
        }
    }

    m.extend({
        clone: function (a, b, c) {
            var d, e, f, g, h, i = m.contains(a.ownerDocument, a);
            if (k.html5Clone || m.isXMLDoc(a) || !ga.test("<" + a.nodeName + ">") ? f = a.cloneNode(!0) : (ta.innerHTML = a.outerHTML, ta.removeChild(f = ta.firstChild)), !(k.noCloneEvent && k.noCloneChecked || 1 !== a.nodeType && 11 !== a.nodeType || m.isXMLDoc(a))) {
                for (d = ua(f), h = ua(a), g = 0; null != (e = h[g]); ++g) {
                    d[g] && Ba(e, d[g])
                }
            }
            if (b) {
                if (c) {
                    for (h = h || ua(a), d = d || ua(f), g = 0; null != (e = h[g]); g++) {
                        Aa(e, d[g])
                    }
                } else {
                    Aa(a, f)
                }
            }
            return d = ua(f, "script"), d.length > 0 && za(d, !i && ua(a, "script")), d = h = e = null, f
        }, buildFragment: function (a, b, c, d) {
            for (var e, f, g, h, i, j, l, n = a.length, o = da(b), p = [], q = 0; n > q; q++) {
                if (f = a[q], f || 0 === f) {
                    if ("object" === m.type(f)) {
                        m.merge(p, f.nodeType ? [f] : f)
                    } else {
                        if (la.test(f)) {
                            h = h || o.appendChild(b.createElement("div")), i = (ja.exec(f) || ["", ""])[1].toLowerCase(), l = ra[i] || ra._default, h.innerHTML = l[1] + f.replace(ia, "<$1></$2>") + l[2], e = l[0];
                            while (e--) {
                                h = h.lastChild
                            }
                            if (!k.leadingWhitespace && ha.test(f) && p.push(b.createTextNode(ha.exec(f)[0])), !k.tbody) {
                                f = "table" !== i || ka.test(f) ? "<table>" !== l[1] || ka.test(f) ? 0 : h : h.firstChild, e = f && f.childNodes.length;
                                while (e--) {
                                    m.nodeName(j = f.childNodes[e], "tbody") && !j.childNodes.length && f.removeChild(j)
                                }
                            }
                            m.merge(p, h.childNodes), h.textContent = "";
                            while (h.firstChild) {
                                h.removeChild(h.firstChild)
                            }
                            h = o.lastChild
                        } else {
                            p.push(b.createTextNode(f))
                        }
                    }
                }
            }
            h && o.removeChild(h), k.appendChecked || m.grep(ua(p, "input"), va), q = 0;
            while (f = p[q++]) {
                if ((!d || -1 === m.inArray(f, d)) && (g = m.contains(f.ownerDocument, f), h = ua(o.appendChild(f), "script"), g && za(h), c)) {
                    e = 0;
                    while (f = h[e++]) {
                        oa.test(f.type || "") && c.push(f)
                    }
                }
            }
            return h = null, o
        }, cleanData: function (a, b) {
            for (var d, e, f, g, h = 0, i = m.expando, j = m.cache, l = k.deleteExpando, n = m.event.special; null != (d = a[h]); h++) {
                if ((b || m.acceptData(d)) && (f = d[i], g = f && j[f])) {
                    if (g.events) {
                        for (e in g.events) {
                            n[e] ? m.event.remove(d, e) : m.removeEvent(d, e, g.handle)
                        }
                    }
                    j[f] && (delete j[f], l ? delete d[i] : typeof d.removeAttribute !== K ? d.removeAttribute(i) : d[i] = null, c.push(f))
                }
            }
        }
    }), m.fn.extend({
        text: function (a) {
            return V(this, function (a) {
                return void 0 === a ? m.text(this) : this.empty().append((this[0] && this[0].ownerDocument || y).createTextNode(a))
            }, null, a, arguments.length)
        }, append: function () {
            return this.domManip(arguments, function (a) {
                if (1 === this.nodeType || 11 === this.nodeType || 9 === this.nodeType) {
                    var b = wa(this, a);
                    b.appendChild(a)
                }
            })
        }, prepend: function () {
            return this.domManip(arguments, function (a) {
                if (1 === this.nodeType || 11 === this.nodeType || 9 === this.nodeType) {
                    var b = wa(this, a);
                    b.insertBefore(a, b.firstChild)
                }
            })
        }, before: function () {
            return this.domManip(arguments, function (a) {
                this.parentNode && this.parentNode.insertBefore(a, this)
            })
        }, after: function () {
            return this.domManip(arguments, function (a) {
                this.parentNode && this.parentNode.insertBefore(a, this.nextSibling)
            })
        }, remove: function (a, b) {
            for (var c, d = a ? m.filter(a, this) : this, e = 0; null != (c = d[e]); e++) {
                b || 1 !== c.nodeType || m.cleanData(ua(c)), c.parentNode && (b && m.contains(c.ownerDocument, c) && za(ua(c, "script")), c.parentNode.removeChild(c))
            }
            return this
        }, empty: function () {
            for (var a, b = 0; null != (a = this[b]); b++) {
                1 === a.nodeType && m.cleanData(ua(a, !1));
                while (a.firstChild) {
                    a.removeChild(a.firstChild)
                }
                a.options && m.nodeName(a, "select") && (a.options.length = 0)
            }
            return this
        }, clone: function (a, b) {
            return a = null == a ? !1 : a, b = null == b ? a : b, this.map(function () {
                return m.clone(this, a, b)
            })
        }, html: function (a) {
            return V(this, function (a) {
                var b = this[0] || {}, c = 0, d = this.length;
                if (void 0 === a) {
                    return 1 === b.nodeType ? b.innerHTML.replace(fa, "") : void 0
                }
                if (!("string" != typeof a || ma.test(a) || !k.htmlSerialize && ga.test(a) || !k.leadingWhitespace && ha.test(a) || ra[(ja.exec(a) || ["", ""])[1].toLowerCase()])) {
                    a = a.replace(ia, "<$1></$2>");
                    try {
                        for (; d > c; c++) {
                            b = this[c] || {}, 1 === b.nodeType && (m.cleanData(ua(b, !1)), b.innerHTML = a)
                        }
                        b = 0
                    } catch (e) {
                    }
                }
                b && this.empty().append(a)
            }, null, a, arguments.length)
        }, replaceWith: function () {
            var a = arguments[0];
            return this.domManip(arguments, function (b) {
                a = this.parentNode, m.cleanData(ua(this)), a && a.replaceChild(b, this)
            }), a && (a.length || a.nodeType) ? this : this.remove()
        }, detach: function (a) {
            return this.remove(a, !0)
        }, domManip: function (a, b) {
            a = e.apply([], a);
            var c, d, f, g, h, i, j = 0, l = this.length, n = this, o = l - 1, p = a[0], q = m.isFunction(p);
            if (q || l > 1 && "string" == typeof p && !k.checkClone && na.test(p)) {
                return this.each(function (c) {
                    var d = n.eq(c);
                    q && (a[0] = p.call(this, c, d.html())), d.domManip(a, b)
                })
            }
            if (l && (i = m.buildFragment(a, this[0].ownerDocument, !1, this), c = i.firstChild, 1 === i.childNodes.length && (i = c), c)) {
                for (g = m.map(ua(i, "script"), xa), f = g.length; l > j; j++) {
                    d = i, j !== o && (d = m.clone(d, !0, !0), f && m.merge(g, ua(d, "script"))), b.call(this[j], d, j)
                }
                if (f) {
                    for (h = g[g.length - 1].ownerDocument, m.map(g, ya), j = 0; f > j; j++) {
                        d = g[j], oa.test(d.type || "") && !m._data(d, "globalEval") && m.contains(h, d) && (d.src ? m._evalUrl && m._evalUrl(d.src) : m.globalEval((d.text || d.textContent || d.innerHTML || "").replace(qa, "")))
                    }
                }
                i = c = null
            }
            return this
        }
    }), m.each({
        appendTo: "append",
        prependTo: "prepend",
        insertBefore: "before",
        insertAfter: "after",
        replaceAll: "replaceWith"
    }, function (a, b) {
        m.fn[a] = function (a) {
            for (var c, d = 0, e = [], g = m(a), h = g.length - 1; h >= d; d++) {
                c = d === h ? this : this.clone(!0), m(g[d])[b](c), f.apply(e, c.get())
            }
            return this.pushStack(e)
        }
    });
    var Ca, Da = {};

    function Ea(b, c) {
        var d, e = m(c.createElement(b)).appendTo(c.body),
            f = a.getDefaultComputedStyle && (d = a.getDefaultComputedStyle(e[0])) ? d.display : m.css(e[0], "display");
        return e.detach(), f
    }

    function Fa(a) {
        var b = y, c = Da[a];
        return c || (c = Ea(a, b), "none" !== c && c || (Ca = (Ca || m("<iframe frameborder='0' width='0' height='0'/>")).appendTo(b.documentElement), b = (Ca[0].contentWindow || Ca[0].contentDocument).document, b.write(), b.close(), c = Ea(a, b), Ca.detach()), Da[a] = c), c
    }

    !function () {
        var a;
        k.shrinkWrapBlocks = function () {
            if (null != a) {
                return a
            }
            a = !1;
            var b, c, d;
            return c = y.getElementsByTagName("body")[0], c && c.style ? (b = y.createElement("div"), d = y.createElement("div"), d.style.cssText = "position:absolute;border:0;width:0;height:0;top:0;left:-9999px", c.appendChild(d).appendChild(b), typeof b.style.zoom !== K && (b.style.cssText = "-webkit-box-sizing:content-box;-moz-box-sizing:content-box;box-sizing:content-box;display:block;margin:0;border:0;padding:1px;width:1px;zoom:1", b.appendChild(y.createElement("div")).style.width = "5px", a = 3 !== b.offsetWidth), c.removeChild(d), a) : void 0
        }
    }();
    var Ga = /^margin/, Ha = new RegExp("^(" + S + ")(?!px)[a-z%]+$", "i"), Ia, Ja, Ka = /^(top|right|bottom|left)$/;
    a.getComputedStyle ? (Ia = function (b) {
        return b.ownerDocument.defaultView.opener ? b.ownerDocument.defaultView.getComputedStyle(b, null) : a.getComputedStyle(b, null)
    }, Ja = function (a, b, c) {
        var d, e, f, g, h = a.style;
        return c = c || Ia(a), g = c ? c.getPropertyValue(b) || c[b] : void 0, c && ("" !== g || m.contains(a.ownerDocument, a) || (g = m.style(a, b)), Ha.test(g) && Ga.test(b) && (d = h.width, e = h.minWidth, f = h.maxWidth, h.minWidth = h.maxWidth = h.width = g, g = c.width, h.width = d, h.minWidth = e, h.maxWidth = f)), void 0 === g ? g : g + ""
    }) : y.documentElement.currentStyle && (Ia = function (a) {
        return a.currentStyle
    }, Ja = function (a, b, c) {
        var d, e, f, g, h = a.style;
        return c = c || Ia(a), g = c ? c[b] : void 0, null == g && h && h[b] && (g = h[b]), Ha.test(g) && !Ka.test(b) && (d = h.left, e = a.runtimeStyle, f = e && e.left, f && (e.left = a.currentStyle.left), h.left = "fontSize" === b ? "1em" : g, g = h.pixelLeft + "px", h.left = d, f && (e.left = f)), void 0 === g ? g : g + "" || "auto"
    });

    function La(a, b) {
        return {
            get: function () {
                var c = a();
                if (null != c) {
                    return c ? void delete this.get : (this.get = b).apply(this, arguments)
                }
            }
        }
    }

    !function () {
        var b, c, d, e, f, g, h;
        if (b = y.createElement("div"), b.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>", d = b.getElementsByTagName("a")[0], c = d && d.style) {
            c.cssText = "float:left;opacity:.5", k.opacity = "0.5" === c.opacity, k.cssFloat = !!c.cssFloat, b.style.backgroundClip = "content-box", b.cloneNode(!0).style.backgroundClip = "", k.clearCloneStyle = "content-box" === b.style.backgroundClip, k.boxSizing = "" === c.boxSizing || "" === c.MozBoxSizing || "" === c.WebkitBoxSizing, m.extend(k, {
                reliableHiddenOffsets: function () {
                    return null == g && i(), g
                }, boxSizingReliable: function () {
                    return null == f && i(), f
                }, pixelPosition: function () {
                    return null == e && i(), e
                }, reliableMarginRight: function () {
                    return null == h && i(), h
                }
            });

            function i() {
                var b, c, d, i;
                c = y.getElementsByTagName("body")[0], c && c.style && (b = y.createElement("div"), d = y.createElement("div"), d.style.cssText = "position:absolute;border:0;width:0;height:0;top:0;left:-9999px", c.appendChild(d).appendChild(b), b.style.cssText = "-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box;display:block;margin-top:1%;top:1%;border:1px;padding:1px;width:4px;position:absolute", e = f = !1, h = !0, a.getComputedStyle && (e = "1%" !== (a.getComputedStyle(b, null) || {}).top, f = "4px" === (a.getComputedStyle(b, null) || {width: "4px"}).width, i = b.appendChild(y.createElement("div")), i.style.cssText = b.style.cssText = "-webkit-box-sizing:content-box;-moz-box-sizing:content-box;box-sizing:content-box;display:block;margin:0;border:0;padding:0", i.style.marginRight = i.style.width = "0", b.style.width = "1px", h = !parseFloat((a.getComputedStyle(i, null) || {}).marginRight), b.removeChild(i)), b.innerHTML = "<table><tr><td></td><td>t</td></tr></table>", i = b.getElementsByTagName("td"), i[0].style.cssText = "margin:0;border:0;padding:0;display:none", g = 0 === i[0].offsetHeight, g && (i[0].style.display = "", i[1].style.display = "none", g = 0 === i[0].offsetHeight), c.removeChild(d))
            }
        }
    }(), m.swap = function (a, b, c, d) {
        var e, f, g = {};
        for (f in b) {
            g[f] = a.style[f], a.style[f] = b[f]
        }
        e = c.apply(a, d || []);
        for (f in b) {
            a.style[f] = g[f]
        }
        return e
    };
    var Ma = /alpha\([^)]*\)/i, Na = /opacity\s*=\s*([^)]*)/, Oa = /^(none|table(?!-c[ea]).+)/,
        Pa = new RegExp("^(" + S + ")(.*)$", "i"), Qa = new RegExp("^([+-])=(" + S + ")", "i"),
        Ra = {position: "absolute", visibility: "hidden", display: "block"},
        Sa = {letterSpacing: "0", fontWeight: "400"}, Ta = ["Webkit", "O", "Moz", "ms"];

    function Ua(a, b) {
        if (b in a) {
            return b
        }
        var c = b.charAt(0).toUpperCase() + b.slice(1), d = b, e = Ta.length;
        while (e--) {
            if (b = Ta[e] + c, b in a) {
                return b
            }
        }
        return d
    }

    function Va(a, b) {
        for (var c, d, e, f = [], g = 0, h = a.length; h > g; g++) {
            d = a[g], d.style && (f[g] = m._data(d, "olddisplay"), c = d.style.display, b ? (f[g] || "none" !== c || (d.style.display = ""), "" === d.style.display && U(d) && (f[g] = m._data(d, "olddisplay", Fa(d.nodeName)))) : (e = U(d), (c && "none" !== c || !e) && m._data(d, "olddisplay", e ? c : m.css(d, "display"))))
        }
        for (g = 0; h > g; g++) {
            d = a[g], d.style && (b && "none" !== d.style.display && "" !== d.style.display || (d.style.display = b ? f[g] || "" : "none"))
        }
        return a
    }

    function Wa(a, b, c) {
        var d = Pa.exec(b);
        return d ? Math.max(0, d[1] - (c || 0)) + (d[2] || "px") : b
    }

    function Xa(a, b, c, d, e) {
        for (var f = c === (d ? "border" : "content") ? 4 : "width" === b ? 1 : 0, g = 0; 4 > f; f += 2) {
            "margin" === c && (g += m.css(a, c + T[f], !0, e)), d ? ("content" === c && (g -= m.css(a, "padding" + T[f], !0, e)), "margin" !== c && (g -= m.css(a, "border" + T[f] + "Width", !0, e))) : (g += m.css(a, "padding" + T[f], !0, e), "padding" !== c && (g += m.css(a, "border" + T[f] + "Width", !0, e)))
        }
        return g
    }

    function Ya(a, b, c) {
        var d = !0, e = "width" === b ? a.offsetWidth : a.offsetHeight, f = Ia(a),
            g = k.boxSizing && "border-box" === m.css(a, "boxSizing", !1, f);
        if (0 >= e || null == e) {
            if (e = Ja(a, b, f), (0 > e || null == e) && (e = a.style[b]), Ha.test(e)) {
                return e
            }
            d = g && (k.boxSizingReliable() || e === a.style[b]), e = parseFloat(e) || 0
        }
        return e + Xa(a, b, c || (g ? "border" : "content"), d, f) + "px"
    }

    m.extend({
        cssHooks: {
            opacity: {
                get: function (a, b) {
                    if (b) {
                        var c = Ja(a, "opacity");
                        return "" === c ? "1" : c
                    }
                }
            }
        },
        cssNumber: {
            columnCount: !0,
            fillOpacity: !0,
            flexGrow: !0,
            flexShrink: !0,
            fontWeight: !0,
            lineHeight: !0,
            opacity: !0,
            order: !0,
            orphans: !0,
            widows: !0,
            zIndex: !0,
            zoom: !0
        },
        cssProps: {"float": k.cssFloat ? "cssFloat" : "styleFloat"},
        style: function (a, b, c, d) {
            if (a && 3 !== a.nodeType && 8 !== a.nodeType && a.style) {
                var e, f, g, h = m.camelCase(b), i = a.style;
                if (b = m.cssProps[h] || (m.cssProps[h] = Ua(i, h)), g = m.cssHooks[b] || m.cssHooks[h], void 0 === c) {
                    return g && "get" in g && void 0 !== (e = g.get(a, !1, d)) ? e : i[b]
                }
                if (f = typeof c, "string" === f && (e = Qa.exec(c)) && (c = (e[1] + 1) * e[2] + parseFloat(m.css(a, b)), f = "number"), null != c && c === c && ("number" !== f || m.cssNumber[h] || (c += "px"), k.clearCloneStyle || "" !== c || 0 !== b.indexOf("background") || (i[b] = "inherit"), !(g && "set" in g && void 0 === (c = g.set(a, c, d))))) {
                    try {
                        i[b] = c
                    } catch (j) {
                    }
                }
            }
        },
        css: function (a, b, c, d) {
            var e, f, g, h = m.camelCase(b);
            return b = m.cssProps[h] || (m.cssProps[h] = Ua(a.style, h)), g = m.cssHooks[b] || m.cssHooks[h], g && "get" in g && (f = g.get(a, !0, c)), void 0 === f && (f = Ja(a, b, d)), "normal" === f && b in Sa && (f = Sa[b]), "" === c || c ? (e = parseFloat(f), c === !0 || m.isNumeric(e) ? e || 0 : f) : f
        }
    }), m.each(["height", "width"], function (a, b) {
        m.cssHooks[b] = {
            get: function (a, c, d) {
                return c ? Oa.test(m.css(a, "display")) && 0 === a.offsetWidth ? m.swap(a, Ra, function () {
                    return Ya(a, b, d)
                }) : Ya(a, b, d) : void 0
            }, set: function (a, c, d) {
                var e = d && Ia(a);
                return Wa(a, c, d ? Xa(a, b, d, k.boxSizing && "border-box" === m.css(a, "boxSizing", !1, e), e) : 0)
            }
        }
    }), k.opacity || (m.cssHooks.opacity = {
        get: function (a, b) {
            return Na.test((b && a.currentStyle ? a.currentStyle.filter : a.style.filter) || "") ? 0.01 * parseFloat(RegExp.$1) + "" : b ? "1" : ""
        }, set: function (a, b) {
            var c = a.style, d = a.currentStyle, e = m.isNumeric(b) ? "alpha(opacity=" + 100 * b + ")" : "",
                f = d && d.filter || c.filter || "";
            c.zoom = 1, (b >= 1 || "" === b) && "" === m.trim(f.replace(Ma, "")) && c.removeAttribute && (c.removeAttribute("filter"), "" === b || d && !d.filter) || (c.filter = Ma.test(f) ? f.replace(Ma, e) : f + " " + e)
        }
    }), m.cssHooks.marginRight = La(k.reliableMarginRight, function (a, b) {
        return b ? m.swap(a, {display: "inline-block"}, Ja, [a, "marginRight"]) : void 0
    }), m.each({margin: "", padding: "", border: "Width"}, function (a, b) {
        m.cssHooks[a + b] = {
            expand: function (c) {
                for (var d = 0, e = {}, f = "string" == typeof c ? c.split(" ") : [c]; 4 > d; d++) {
                    e[a + T[d] + b] = f[d] || f[d - 2] || f[0]
                }
                return e
            }
        }, Ga.test(a) || (m.cssHooks[a + b].set = Wa)
    }), m.fn.extend({
        css: function (a, b) {
            return V(this, function (a, b, c) {
                var d, e, f = {}, g = 0;
                if (m.isArray(b)) {
                    for (d = Ia(a), e = b.length; e > g; g++) {
                        f[b[g]] = m.css(a, b[g], !1, d)
                    }
                    return f
                }
                return void 0 !== c ? m.style(a, b, c) : m.css(a, b)
            }, a, b, arguments.length > 1)
        }, show: function () {
            return Va(this, !0)
        }, hide: function () {
            return Va(this)
        }, toggle: function (a) {
            return "boolean" == typeof a ? a ? this.show() : this.hide() : this.each(function () {
                U(this) ? m(this).show() : m(this).hide()
            })
        }
    });

    function Za(a, b, c, d, e) {
        return new Za.prototype.init(a, b, c, d, e)
    }

    m.Tween = Za, Za.prototype = {
        constructor: Za, init: function (a, b, c, d, e, f) {
            this.elem = a, this.prop = c, this.easing = e || "swing", this.options = b, this.start = this.now = this.cur(), this.end = d, this.unit = f || (m.cssNumber[c] ? "" : "px")
        }, cur: function () {
            var a = Za.propHooks[this.prop];
            return a && a.get ? a.get(this) : Za.propHooks._default.get(this)
        }, run: function (a) {
            var b, c = Za.propHooks[this.prop];
            return this.options.duration ? this.pos = b = m.easing[this.easing](a, this.options.duration * a, 0, 1, this.options.duration) : this.pos = b = a, this.now = (this.end - this.start) * b + this.start, this.options.step && this.options.step.call(this.elem, this.now, this), c && c.set ? c.set(this) : Za.propHooks._default.set(this), this
        }
    }, Za.prototype.init.prototype = Za.prototype, Za.propHooks = {
        _default: {
            get: function (a) {
                var b;
                return null == a.elem[a.prop] || a.elem.style && null != a.elem.style[a.prop] ? (b = m.css(a.elem, a.prop, ""), b && "auto" !== b ? b : 0) : a.elem[a.prop]
            }, set: function (a) {
                m.fx.step[a.prop] ? m.fx.step[a.prop](a) : a.elem.style && (null != a.elem.style[m.cssProps[a.prop]] || m.cssHooks[a.prop]) ? m.style(a.elem, a.prop, a.now + a.unit) : a.elem[a.prop] = a.now
            }
        }
    }, Za.propHooks.scrollTop = Za.propHooks.scrollLeft = {
        set: function (a) {
            a.elem.nodeType && a.elem.parentNode && (a.elem[a.prop] = a.now)
        }
    }, m.easing = {
        linear: function (a) {
            return a
        }, swing: function (a) {
            return 0.5 - Math.cos(a * Math.PI) / 2
        }
    }, m.fx = Za.prototype.init, m.fx.step = {};
    var $a, _a, ab = /^(?:toggle|show|hide)$/, bb = new RegExp("^(?:([+-])=|)(" + S + ")([a-z%]*)$", "i"),
        cb = /queueHooks$/, db = [ib], eb = {
            "*": [function (a, b) {
                var c = this.createTween(a, b), d = c.cur(), e = bb.exec(b), f = e && e[3] || (m.cssNumber[a] ? "" : "px"),
                    g = (m.cssNumber[a] || "px" !== f && +d) && bb.exec(m.css(c.elem, a)), h = 1, i = 20;
                if (g && g[3] !== f) {
                    f = f || g[3], e = e || [], g = +d || 1;
                    do {
                        h = h || ".5", g /= h, m.style(c.elem, a, g + f)
                    } while (h !== (h = c.cur() / d) && 1 !== h && --i)
                }
                return e && (g = c.start = +g || +d || 0, c.unit = f, c.end = e[1] ? g + (e[1] + 1) * e[2] : +e[2]), c
            }]
        };

    function fb() {
        return setTimeout(function () {
            $a = void 0
        }), $a = m.now()
    }

    function gb(a, b) {
        var c, d = {height: a}, e = 0;
        for (b = b ? 1 : 0; 4 > e; e += 2 - b) {
            c = T[e], d["margin" + c] = d["padding" + c] = a
        }
        return b && (d.opacity = d.width = a), d
    }

    function hb(a, b, c) {
        for (var d, e = (eb[b] || []).concat(eb["*"]), f = 0, g = e.length; g > f; f++) {
            if (d = e[f].call(c, b, a)) {
                return d
            }
        }
    }

    function ib(a, b, c) {
        var d, e, f, g, h, i, j, l, n = this, o = {}, p = a.style, q = a.nodeType && U(a), r = m._data(a, "fxshow");
        c.queue || (h = m._queueHooks(a, "fx"), null == h.unqueued && (h.unqueued = 0, i = h.empty.fire, h.empty.fire = function () {
            h.unqueued || i()
        }), h.unqueued++, n.always(function () {
            n.always(function () {
                h.unqueued--, m.queue(a, "fx").length || h.empty.fire()
            })
        })), 1 === a.nodeType && ("height" in b || "width" in b) && (c.overflow = [p.overflow, p.overflowX, p.overflowY], j = m.css(a, "display"), l = "none" === j ? m._data(a, "olddisplay") || Fa(a.nodeName) : j, "inline" === l && "none" === m.css(a, "float") && (k.inlineBlockNeedsLayout && "inline" !== Fa(a.nodeName) ? p.zoom = 1 : p.display = "inline-block")), c.overflow && (p.overflow = "hidden", k.shrinkWrapBlocks() || n.always(function () {
            p.overflow = c.overflow[0], p.overflowX = c.overflow[1], p.overflowY = c.overflow[2]
        }));
        for (d in b) {
            if (e = b[d], ab.exec(e)) {
                if (delete b[d], f = f || "toggle" === e, e === (q ? "hide" : "show")) {
                    if ("show" !== e || !r || void 0 === r[d]) {
                        continue
                    }
                    q = !0
                }
                o[d] = r && r[d] || m.style(a, d)
            } else {
                j = void 0
            }
        }
        if (m.isEmptyObject(o)) {
            "inline" === ("none" === j ? Fa(a.nodeName) : j) && (p.display = j)
        } else {
            r ? "hidden" in r && (q = r.hidden) : r = m._data(a, "fxshow", {}), f && (r.hidden = !q), q ? m(a).show() : n.done(function () {
                m(a).hide()
            }), n.done(function () {
                var b;
                m._removeData(a, "fxshow");
                for (b in o) {
                    m.style(a, b, o[b])
                }
            });
            for (d in o) {
                g = hb(q ? r[d] : 0, d, n), d in r || (r[d] = g.start, q && (g.end = g.start, g.start = "width" === d || "height" === d ? 1 : 0))
            }
        }
    }

    function jb(a, b) {
        var c, d, e, f, g;
        for (c in a) {
            if (d = m.camelCase(c), e = b[d], f = a[c], m.isArray(f) && (e = f[1], f = a[c] = f[0]), c !== d && (a[d] = f, delete a[c]), g = m.cssHooks[d], g && "expand" in g) {
                f = g.expand(f), delete a[d];
                for (c in f) {
                    c in a || (a[c] = f[c], b[c] = e)
                }
            } else {
                b[d] = e
            }
        }
    }

    function kb(a, b, c) {
        var d, e, f = 0, g = db.length, h = m.Deferred().always(function () {
            delete i.elem
        }), i = function () {
            if (e) {
                return !1
            }
            for (var b = $a || fb(), c = Math.max(0, j.startTime + j.duration - b), d = c / j.duration || 0, f = 1 - d, g = 0, i = j.tweens.length; i > g; g++) {
                j.tweens[g].run(f)
            }
            return h.notifyWith(a, [j, f, c]), 1 > f && i ? c : (h.resolveWith(a, [j]), !1)
        }, j = h.promise({
            elem: a,
            props: m.extend({}, b),
            opts: m.extend(!0, {specialEasing: {}}, c),
            originalProperties: b,
            originalOptions: c,
            startTime: $a || fb(),
            duration: c.duration,
            tweens: [],
            createTween: function (b, c) {
                var d = m.Tween(a, j.opts, b, c, j.opts.specialEasing[b] || j.opts.easing);
                return j.tweens.push(d), d
            },
            stop: function (b) {
                var c = 0, d = b ? j.tweens.length : 0;
                if (e) {
                    return this
                }
                for (e = !0; d > c; c++) {
                    j.tweens[c].run(1)
                }
                return b ? h.resolveWith(a, [j, b]) : h.rejectWith(a, [j, b]), this
            }
        }), k = j.props;
        for (jb(k, j.opts.specialEasing); g > f; f++) {
            if (d = db[f].call(j, a, k, j.opts)) {
                return d
            }
        }
        return m.map(k, hb, j), m.isFunction(j.opts.start) && j.opts.start.call(a, j), m.fx.timer(m.extend(i, {
            elem: a,
            anim: j,
            queue: j.opts.queue
        })), j.progress(j.opts.progress).done(j.opts.done, j.opts.complete).fail(j.opts.fail).always(j.opts.always)
    }

    m.Animation = m.extend(kb, {
        tweener: function (a, b) {
            m.isFunction(a) ? (b = a, a = ["*"]) : a = a.split(" ");
            for (var c, d = 0, e = a.length; e > d; d++) {
                c = a[d], eb[c] = eb[c] || [], eb[c].unshift(b)
            }
        }, prefilter: function (a, b) {
            b ? db.unshift(a) : db.push(a)
        }
    }), m.speed = function (a, b, c) {
        var d = a && "object" == typeof a ? m.extend({}, a) : {
            complete: c || !c && b || m.isFunction(a) && a,
            duration: a,
            easing: c && b || b && !m.isFunction(b) && b
        };
        return d.duration = m.fx.off ? 0 : "number" == typeof d.duration ? d.duration : d.duration in m.fx.speeds ? m.fx.speeds[d.duration] : m.fx.speeds._default, (null == d.queue || d.queue === !0) && (d.queue = "fx"), d.old = d.complete, d.complete = function () {
            m.isFunction(d.old) && d.old.call(this), d.queue && m.dequeue(this, d.queue)
        }, d
    }, m.fn.extend({
        fadeTo: function (a, b, c, d) {
            return this.filter(U).css("opacity", 0).show().end().animate({opacity: b}, a, c, d)
        }, animate: function (a, b, c, d) {
            var e = m.isEmptyObject(a), f = m.speed(b, c, d), g = function () {
                var b = kb(this, m.extend({}, a), f);
                (e || m._data(this, "finish")) && b.stop(!0)
            };
            return g.finish = g, e || f.queue === !1 ? this.each(g) : this.queue(f.queue, g)
        }, stop: function (a, b, c) {
            var d = function (a) {
                var b = a.stop;
                delete a.stop, b(c)
            };
            return "string" != typeof a && (c = b, b = a, a = void 0), b && a !== !1 && this.queue(a || "fx", []), this.each(function () {
                var b = !0, e = null != a && a + "queueHooks", f = m.timers, g = m._data(this);
                if (e) {
                    g[e] && g[e].stop && d(g[e])
                } else {
                    for (e in g) {
                        g[e] && g[e].stop && cb.test(e) && d(g[e])
                    }
                }
                for (e = f.length; e--;) {
                    f[e].elem !== this || null != a && f[e].queue !== a || (f[e].anim.stop(c), b = !1, f.splice(e, 1))
                }
                (b || !c) && m.dequeue(this, a)
            })
        }, finish: function (a) {
            return a !== !1 && (a = a || "fx"), this.each(function () {
                var b, c = m._data(this), d = c[a + "queue"], e = c[a + "queueHooks"], f = m.timers,
                    g = d ? d.length : 0;
                for (c.finish = !0, m.queue(this, a, []), e && e.stop && e.stop.call(this, !0), b = f.length; b--;) {
                    f[b].elem === this && f[b].queue === a && (f[b].anim.stop(!0), f.splice(b, 1))
                }
                for (b = 0; g > b; b++) {
                    d[b] && d[b].finish && d[b].finish.call(this)
                }
                delete c.finish
            })
        }
    }), m.each(["toggle", "show", "hide"], function (a, b) {
        var c = m.fn[b];
        m.fn[b] = function (a, d, e) {
            return null == a || "boolean" == typeof a ? c.apply(this, arguments) : this.animate(gb(b, !0), a, d, e)
        }
    }), m.each({
        slideDown: gb("show"),
        slideUp: gb("hide"),
        slideToggle: gb("toggle"),
        fadeIn: {opacity: "show"},
        fadeOut: {opacity: "hide"},
        fadeToggle: {opacity: "toggle"}
    }, function (a, b) {
        m.fn[a] = function (a, c, d) {
            return this.animate(b, a, c, d)
        }
    }), m.timers = [], m.fx.tick = function () {
        var a, b = m.timers, c = 0;
        for ($a = m.now(); c < b.length; c++) {
            a = b[c], a() || b[c] !== a || b.splice(c--, 1)
        }
        b.length || m.fx.stop(), $a = void 0
    }, m.fx.timer = function (a) {
        m.timers.push(a), a() ? m.fx.start() : m.timers.pop()
    }, m.fx.interval = 13, m.fx.start = function () {
        _a || (_a = setInterval(m.fx.tick, m.fx.interval))
    }, m.fx.stop = function () {
        clearInterval(_a), _a = null
    }, m.fx.speeds = {slow: 600, fast: 200, _default: 400}, m.fn.delay = function (a, b) {
        return a = m.fx ? m.fx.speeds[a] || a : a, b = b || "fx", this.queue(b, function (b, c) {
            var d = setTimeout(b, a);
            c.stop = function () {
                clearTimeout(d)
            }
        })
    }, function () {
        var a, b, c, d, e;
        b = y.createElement("div"), b.setAttribute("className", "t"), b.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>", d = b.getElementsByTagName("a")[0], c = y.createElement("select"), e = c.appendChild(y.createElement("option")), a = b.getElementsByTagName("input")[0], d.style.cssText = "top:1px", k.getSetAttribute = "t" !== b.className, k.style = /top/.test(d.getAttribute("style")), k.hrefNormalized = "/a" === d.getAttribute("href"), k.checkOn = !!a.value, k.optSelected = e.selected, k.enctype = !!y.createElement("form").enctype, c.disabled = !0, k.optDisabled = !e.disabled, a = y.createElement("input"), a.setAttribute("value", ""), k.input = "" === a.getAttribute("value"), a.value = "t", a.setAttribute("type", "radio"), k.radioValue = "t" === a.value
    }();
    var lb = /\r/g;
    m.fn.extend({
        val: function (a) {
            var b, c, d, e = this[0];
            if (arguments.length) {
                return d = m.isFunction(a), this.each(function (c) {
                    var e;
                    1 === this.nodeType && (e = d ? a.call(this, c, m(this).val()) : a, null == e ? e = "" : "number" == typeof e ? e += "" : m.isArray(e) && (e = m.map(e, function (a) {
                        return null == a ? "" : a + ""
                    })), b = m.valHooks[this.type] || m.valHooks[this.nodeName.toLowerCase()], b && "set" in b && void 0 !== b.set(this, e, "value") || (this.value = e))
                })
            }
            if (e) {
                return b = m.valHooks[e.type] || m.valHooks[e.nodeName.toLowerCase()], b && "get" in b && void 0 !== (c = b.get(e, "value")) ? c : (c = e.value, "string" == typeof c ? c.replace(lb, "") : null == c ? "" : c)
            }
        }
    }), m.extend({
        valHooks: {
            option: {
                get: function (a) {
                    var b = m.find.attr(a, "value");
                    return null != b ? b : m.trim(m.text(a))
                }
            }, select: {
                get: function (a) {
                    for (var b, c, d = a.options, e = a.selectedIndex, f = "select-one" === a.type || 0 > e, g = f ? null : [], h = f ? e + 1 : d.length, i = 0 > e ? h : f ? e : 0; h > i; i++) {
                        if (c = d[i], !(!c.selected && i !== e || (k.optDisabled ? c.disabled : null !== c.getAttribute("disabled")) || c.parentNode.disabled && m.nodeName(c.parentNode, "optgroup"))) {
                            if (b = m(c).val(), f) {
                                return b
                            }
                            g.push(b)
                        }
                    }
                    return g
                }, set: function (a, b) {
                    var c, d, e = a.options, f = m.makeArray(b), g = e.length;
                    while (g--) {
                        if (d = e[g], m.inArray(m.valHooks.option.get(d), f) >= 0) {
                            try {
                                d.selected = c = !0
                            } catch (h) {
                                d.scrollHeight
                            }
                        } else {
                            d.selected = !1
                        }
                    }
                    return c || (a.selectedIndex = -1), e
                }
            }
        }
    }), m.each(["radio", "checkbox"], function () {
        m.valHooks[this] = {
            set: function (a, b) {
                return m.isArray(b) ? a.checked = m.inArray(m(a).val(), b) >= 0 : void 0
            }
        }, k.checkOn || (m.valHooks[this].get = function (a) {
            return null === a.getAttribute("value") ? "on" : a.value
        })
    });
    var mb, nb, ob = m.expr.attrHandle, pb = /^(?:checked|selected)$/i, qb = k.getSetAttribute, rb = k.input;
    m.fn.extend({
        attr: function (a, b) {
            return V(this, m.attr, a, b, arguments.length > 1)
        }, removeAttr: function (a) {
            return this.each(function () {
                m.removeAttr(this, a)
            })
        }
    }), m.extend({
        attr: function (a, b, c) {
            var d, e, f = a.nodeType;
            if (a && 3 !== f && 8 !== f && 2 !== f) {
                return typeof a.getAttribute === K ? m.prop(a, b, c) : (1 === f && m.isXMLDoc(a) || (b = b.toLowerCase(), d = m.attrHooks[b] || (m.expr.match.bool.test(b) ? nb : mb)), void 0 === c ? d && "get" in d && null !== (e = d.get(a, b)) ? e : (e = m.find.attr(a, b), null == e ? void 0 : e) : null !== c ? d && "set" in d && void 0 !== (e = d.set(a, c, b)) ? e : (a.setAttribute(b, c + ""), c) : void m.removeAttr(a, b))
            }
        }, removeAttr: function (a, b) {
            var c, d, e = 0, f = b && b.match(E);
            if (f && 1 === a.nodeType) {
                while (c = f[e++]) {
                    d = m.propFix[c] || c, m.expr.match.bool.test(c) ? rb && qb || !pb.test(c) ? a[d] = !1 : a[m.camelCase("default-" + c)] = a[d] = !1 : m.attr(a, c, ""), a.removeAttribute(qb ? c : d)
                }
            }
        }, attrHooks: {
            type: {
                set: function (a, b) {
                    if (!k.radioValue && "radio" === b && m.nodeName(a, "input")) {
                        var c = a.value;
                        return a.setAttribute("type", b), c && (a.value = c), b
                    }
                }
            }
        }
    }), nb = {
        set: function (a, b, c) {
            return b === !1 ? m.removeAttr(a, c) : rb && qb || !pb.test(c) ? a.setAttribute(!qb && m.propFix[c] || c, c) : a[m.camelCase("default-" + c)] = a[c] = !0, c
        }
    }, m.each(m.expr.match.bool.source.match(/\w+/g), function (a, b) {
        var c = ob[b] || m.find.attr;
        ob[b] = rb && qb || !pb.test(b) ? function (a, b, d) {
            var e, f;
            return d || (f = ob[b], ob[b] = e, e = null != c(a, b, d) ? b.toLowerCase() : null, ob[b] = f), e
        } : function (a, b, c) {
            return c ? void 0 : a[m.camelCase("default-" + b)] ? b.toLowerCase() : null
        }
    }), rb && qb || (m.attrHooks.value = {
        set: function (a, b, c) {
            return m.nodeName(a, "input") ? void (a.defaultValue = b) : mb && mb.set(a, b, c)
        }
    }), qb || (mb = {
        set: function (a, b, c) {
            var d = a.getAttributeNode(c);
            return d || a.setAttributeNode(d = a.ownerDocument.createAttribute(c)), d.value = b += "", "value" === c || b === a.getAttribute(c) ? b : void 0
        }
    }, ob.id = ob.name = ob.coords = function (a, b, c) {
        var d;
        return c ? void 0 : (d = a.getAttributeNode(b)) && "" !== d.value ? d.value : null
    }, m.valHooks.button = {
        get: function (a, b) {
            var c = a.getAttributeNode(b);
            return c && c.specified ? c.value : void 0
        }, set: mb.set
    }, m.attrHooks.contenteditable = {
        set: function (a, b, c) {
            mb.set(a, "" === b ? !1 : b, c)
        }
    }, m.each(["width", "height"], function (a, b) {
        m.attrHooks[b] = {
            set: function (a, c) {
                return "" === c ? (a.setAttribute(b, "auto"), c) : void 0
            }
        }
    })), k.style || (m.attrHooks.style = {
        get: function (a) {
            return a.style.cssText || void 0
        }, set: function (a, b) {
            return a.style.cssText = b + ""
        }
    });
    var sb = /^(?:input|select|textarea|button|object)$/i, tb = /^(?:a|area)$/i;
    m.fn.extend({
        prop: function (a, b) {
            return V(this, m.prop, a, b, arguments.length > 1)
        }, removeProp: function (a) {
            return a = m.propFix[a] || a, this.each(function () {
                try {
                    this[a] = void 0, delete this[a]
                } catch (b) {
                }
            })
        }
    }), m.extend({
        propFix: {"for": "htmlFor", "class": "className"}, prop: function (a, b, c) {
            var d, e, f, g = a.nodeType;
            if (a && 3 !== g && 8 !== g && 2 !== g) {
                return f = 1 !== g || !m.isXMLDoc(a), f && (b = m.propFix[b] || b, e = m.propHooks[b]), void 0 !== c ? e && "set" in e && void 0 !== (d = e.set(a, c, b)) ? d : a[b] = c : e && "get" in e && null !== (d = e.get(a, b)) ? d : a[b]
            }
        }, propHooks: {
            tabIndex: {
                get: function (a) {
                    var b = m.find.attr(a, "tabindex");
                    return b ? parseInt(b, 10) : sb.test(a.nodeName) || tb.test(a.nodeName) && a.href ? 0 : -1
                }
            }
        }
    }), k.hrefNormalized || m.each(["href", "src"], function (a, b) {
        m.propHooks[b] = {
            get: function (a) {
                return a.getAttribute(b, 4)
            }
        }
    }), k.optSelected || (m.propHooks.selected = {
        get: function (a) {
            var b = a.parentNode;
            return b && (b.selectedIndex, b.parentNode && b.parentNode.selectedIndex), null
        }
    }), m.each(["tabIndex", "readOnly", "maxLength", "cellSpacing", "cellPadding", "rowSpan", "colSpan", "useMap", "frameBorder", "contentEditable"], function () {
        m.propFix[this.toLowerCase()] = this
    }), k.enctype || (m.propFix.enctype = "encoding");
    var ub = /[\t\r\n\f]/g;
    m.fn.extend({
        addClass: function (a) {
            var b, c, d, e, f, g, h = 0, i = this.length, j = "string" == typeof a && a;
            if (m.isFunction(a)) {
                return this.each(function (b) {
                    m(this).addClass(a.call(this, b, this.className))
                })
            }
            if (j) {
                for (b = (a || "").match(E) || []; i > h; h++) {
                    if (c = this[h], d = 1 === c.nodeType && (c.className ? (" " + c.className + " ").replace(ub, " ") : " ")) {
                        f = 0;
                        while (e = b[f++]) {
                            d.indexOf(" " + e + " ") < 0 && (d += e + " ")
                        }
                        g = m.trim(d), c.className !== g && (c.className = g)
                    }
                }
            }
            return this
        }, removeClass: function (a) {
            var b, c, d, e, f, g, h = 0, i = this.length, j = 0 === arguments.length || "string" == typeof a && a;
            if (m.isFunction(a)) {
                return this.each(function (b) {
                    m(this).removeClass(a.call(this, b, this.className))
                })
            }
            if (j) {
                for (b = (a || "").match(E) || []; i > h; h++) {
                    if (c = this[h], d = 1 === c.nodeType && (c.className ? (" " + c.className + " ").replace(ub, " ") : "")) {
                        f = 0;
                        while (e = b[f++]) {
                            while (d.indexOf(" " + e + " ") >= 0) {
                                d = d.replace(" " + e + " ", " ")
                            }
                        }
                        g = a ? m.trim(d) : "", c.className !== g && (c.className = g)
                    }
                }
            }
            return this
        }, toggleClass: function (a, b) {
            var c = typeof a;
            return "boolean" == typeof b && "string" === c ? b ? this.addClass(a) : this.removeClass(a) : this.each(m.isFunction(a) ? function (c) {
                m(this).toggleClass(a.call(this, c, this.className, b), b)
            } : function () {
                if ("string" === c) {
                    var b, d = 0, e = m(this), f = a.match(E) || [];
                    while (b = f[d++]) {
                        e.hasClass(b) ? e.removeClass(b) : e.addClass(b)
                    }
                } else {
                    (c === K || "boolean" === c) && (this.className && m._data(this, "__className__", this.className), this.className = this.className || a === !1 ? "" : m._data(this, "__className__") || "")
                }
            })
        }, hasClass: function (a) {
            for (var b = " " + a + " ", c = 0, d = this.length; d > c; c++) {
                if (1 === this[c].nodeType && (" " + this[c].className + " ").replace(ub, " ").indexOf(b) >= 0) {
                    return !0
                }
            }
            return !1
        }
    }), m.each("blur focus focusin focusout load resize scroll unload click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select submit keydown keypress keyup error contextmenu".split(" "), function (a, b) {
        m.fn[b] = function (a, c) {
            return arguments.length > 0 ? this.on(b, null, a, c) : this.trigger(b)
        }
    }), m.fn.extend({
        hover: function (a, b) {
            return this.mouseenter(a).mouseleave(b || a)
        }, bind: function (a, b, c) {
            return this.on(a, null, b, c)
        }, unbind: function (a, b) {
            return this.off(a, null, b)
        }, delegate: function (a, b, c, d) {
            return this.on(b, a, c, d)
        }, undelegate: function (a, b, c) {
            return 1 === arguments.length ? this.off(a, "**") : this.off(b, a || "**", c)
        }
    });
    var vb = m.now(), wb = /\?/,
        xb = /(,)|(\[|{)|(}|])|"(?:[^"\\\r\n]|\\["\\\/bfnrt]|\\u[\da-fA-F]{4})*"\s*:?|true|false|null|-?(?!0\d)\d+(?:\.\d+|)(?:[eE][+-]?\d+|)/g;
    m.parseJSON = function (b) {
        if (a.JSON && a.JSON.parse) {
            return a.JSON.parse(b + "")
        }
        var c, d = null, e = m.trim(b + "");
        return e && !m.trim(e.replace(xb, function (a, b, e, f) {
            return c && b && (d = 0), 0 === d ? a : (c = e || b, d += !f - !e, "")
        })) ? Function("return " + e)() : m.error("Invalid JSON: " + b)
    }, m.parseXML = function (b) {
        var c, d;
        if (!b || "string" != typeof b) {
            return null
        }
        try {
            a.DOMParser ? (d = new DOMParser, c = d.parseFromString(b, "text/xml")) : (c = new ActiveXObject("Microsoft.XMLDOM"), c.async = "false", c.loadXML(b))
        } catch (e) {
            c = void 0
        }
        return c && c.documentElement && !c.getElementsByTagName("parsererror").length || m.error("Invalid XML: " + b), c
    };
    var yb, zb, Ab = /#.*$/, Bb = /([?&])_=[^&]*/, Cb = /^(.*?):[ \t]*([^\r\n]*)\r?$/gm,
        Db = /^(?:about|app|app-storage|.+-extension|file|res|widget):$/, Eb = /^(?:GET|HEAD)$/, Fb = /^\/\//,
        Gb = /^([\w.+-]+:)(?:\/\/(?:[^\/?#]*@|)([^\/?#:]*)(?::(\d+)|)|)/, Hb = {}, Ib = {}, Jb = "*/".concat("*");
    try {
        zb = location.href
    } catch (Kb) {
        zb = y.createElement("a"), zb.href = "", zb = zb.href
    }
    yb = Gb.exec(zb.toLowerCase()) || [];

    function Lb(a) {
        return function (b, c) {
            "string" != typeof b && (c = b, b = "*");
            var d, e = 0, f = b.toLowerCase().match(E) || [];
            if (m.isFunction(c)) {
                while (d = f[e++]) {
                    "+" === d.charAt(0) ? (d = d.slice(1) || "*", (a[d] = a[d] || []).unshift(c)) : (a[d] = a[d] || []).push(c)
                }
            }
        }
    }

    function Mb(a, b, c, d) {
        var e = {}, f = a === Ib;

        function g(h) {
            var i;
            return e[h] = !0, m.each(a[h] || [], function (a, h) {
                var j = h(b, c, d);
                return "string" != typeof j || f || e[j] ? f ? !(i = j) : void 0 : (b.dataTypes.unshift(j), g(j), !1)
            }), i
        }

        return g(b.dataTypes[0]) || !e["*"] && g("*")
    }

    function Nb(a, b) {
        var c, d, e = m.ajaxSettings.flatOptions || {};
        for (d in b) {
            void 0 !== b[d] && ((e[d] ? a : c || (c = {}))[d] = b[d])
        }
        return c && m.extend(!0, a, c), a
    }

    function Ob(a, b, c) {
        var d, e, f, g, h = a.contents, i = a.dataTypes;
        while ("*" === i[0]) {
            i.shift(), void 0 === e && (e = a.mimeType || b.getResponseHeader("Content-Type"))
        }
        if (e) {
            for (g in h) {
                if (h[g] && h[g].test(e)) {
                    i.unshift(g);
                    break
                }
            }
        }
        if (i[0] in c) {
            f = i[0]
        } else {
            for (g in c) {
                if (!i[0] || a.converters[g + " " + i[0]]) {
                    f = g;
                    break
                }
                d || (d = g)
            }
            f = f || d
        }
        return f ? (f !== i[0] && i.unshift(f), c[f]) : void 0
    }

    function Pb(a, b, c, d) {
        var e, f, g, h, i, j = {}, k = a.dataTypes.slice();
        if (k[1]) {
            for (g in a.converters) {
                j[g.toLowerCase()] = a.converters[g]
            }
        }
        f = k.shift();
        while (f) {
            if (a.responseFields[f] && (c[a.responseFields[f]] = b), !i && d && a.dataFilter && (b = a.dataFilter(b, a.dataType)), i = f, f = k.shift()) {
                if ("*" === f) {
                    f = i
                } else {
                    if ("*" !== i && i !== f) {
                        if (g = j[i + " " + f] || j["* " + f], !g) {
                            for (e in j) {
                                if (h = e.split(" "), h[1] === f && (g = j[i + " " + h[0]] || j["* " + h[0]])) {
                                    g === !0 ? g = j[e] : j[e] !== !0 && (f = h[0], k.unshift(h[1]));
                                    break
                                }
                            }
                        }
                        if (g !== !0) {
                            if (g && a["throws"]) {
                                b = g(b)
                            } else {
                                try {
                                    b = g(b)
                                } catch (l) {
                                    return {state: "parsererror", error: g ? l : "No conversion from " + i + " to " + f}
                                }
                            }
                        }
                    }
                }
            }
        }
        return {state: "success", data: b}
    }

    m.extend({
        active: 0,
        lastModified: {},
        etag: {},
        ajaxSettings: {
            url: zb,
            type: "GET",
            isLocal: Db.test(yb[1]),
            global: !0,
            processData: !0,
            async: !0,
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            accepts: {
                "*": Jb,
                text: "text/plain",
                html: "text/html",
                xml: "application/xml, text/xml",
                json: "application/json, text/javascript"
            },
            contents: {xml: /xml/, html: /html/, json: /json/},
            responseFields: {xml: "responseXML", text: "responseText", json: "responseJSON"},
            converters: {"* text": String, "text html": !0, "text json": m.parseJSON, "text xml": m.parseXML},
            flatOptions: {url: !0, context: !0}
        },
        ajaxSetup: function (a, b) {
            return b ? Nb(Nb(a, m.ajaxSettings), b) : Nb(m.ajaxSettings, a)
        },
        ajaxPrefilter: Lb(Hb),
        ajaxTransport: Lb(Ib),
        ajax: function (a, b) {
            "object" == typeof a && (b = a, a = void 0), b = b || {};
            var c, d, e, f, g, h, i, j, k = m.ajaxSetup({}, b), l = k.context || k,
                n = k.context && (l.nodeType || l.jquery) ? m(l) : m.event, o = m.Deferred(),
                p = m.Callbacks("once memory"), q = k.statusCode || {}, r = {}, s = {}, t = 0, u = "canceled", v = {
                    readyState: 0, getResponseHeader: function (a) {
                        var b;
                        if (2 === t) {
                            if (!j) {
                                j = {};
                                while (b = Cb.exec(f)) {
                                    j[b[1].toLowerCase()] = b[2]
                                }
                            }
                            b = j[a.toLowerCase()]
                        }
                        return null == b ? null : b
                    }, getAllResponseHeaders: function () {
                        return 2 === t ? f : null
                    }, setRequestHeader: function (a, b) {
                        var c = a.toLowerCase();
                        return t || (a = s[c] = s[c] || a, r[a] = b), this
                    }, overrideMimeType: function (a) {
                        return t || (k.mimeType = a), this
                    }, statusCode: function (a) {
                        var b;
                        if (a) {
                            if (2 > t) {
                                for (b in a) {
                                    q[b] = [q[b], a[b]]
                                }
                            } else {
                                v.always(a[v.status])
                            }
                        }
                        return this
                    }, abort: function (a) {
                        var b = a || u;
                        return i && i.abort(b), x(0, b), this
                    }
                };
            if (o.promise(v).complete = p.add, v.success = v.done, v.error = v.fail, k.url = ((a || k.url || zb) + "").replace(Ab, "").replace(Fb, yb[1] + "//"), k.type = b.method || b.type || k.method || k.type, k.dataTypes = m.trim(k.dataType || "*").toLowerCase().match(E) || [""], null == k.crossDomain && (c = Gb.exec(k.url.toLowerCase()), k.crossDomain = !(!c || c[1] === yb[1] && c[2] === yb[2] && (c[3] || ("http:" === c[1] ? "80" : "443")) === (yb[3] || ("http:" === yb[1] ? "80" : "443")))), k.data && k.processData && "string" != typeof k.data && (k.data = m.param(k.data, k.traditional)), Mb(Hb, k, b, v), 2 === t) {
                return v
            }
            h = m.event && k.global, h && 0 === m.active++ && m.event.trigger("ajaxStart"), k.type = k.type.toUpperCase(), k.hasContent = !Eb.test(k.type), e = k.url, k.hasContent || (k.data && (e = k.url += (wb.test(e) ? "&" : "?") + k.data, delete k.data), k.cache === !1 && (k.url = Bb.test(e) ? e.replace(Bb, "$1_=" + vb++) : e + (wb.test(e) ? "&" : "?") + "_=" + vb++)), k.ifModified && (m.lastModified[e] && v.setRequestHeader("If-Modified-Since", m.lastModified[e]), m.etag[e] && v.setRequestHeader("If-None-Match", m.etag[e])), (k.data && k.hasContent && k.contentType !== !1 || b.contentType) && v.setRequestHeader("Content-Type", k.contentType), v.setRequestHeader("Accept", k.dataTypes[0] && k.accepts[k.dataTypes[0]] ? k.accepts[k.dataTypes[0]] + ("*" !== k.dataTypes[0] ? ", " + Jb + "; q=0.01" : "") : k.accepts["*"]);
            for (d in k.headers) {
                v.setRequestHeader(d, k.headers[d])
            }
            if (k.beforeSend && (k.beforeSend.call(l, v, k) === !1 || 2 === t)) {
                return v.abort()
            }
            u = "abort";
            for (d in {success: 1, error: 1, complete: 1}) {
                v[d](k[d])
            }
            if (i = Mb(Ib, k, b, v)) {
                v.readyState = 1, h && n.trigger("ajaxSend", [v, k]), k.async && k.timeout > 0 && (g = setTimeout(function () {
                    v.abort("timeout")
                }, k.timeout));
                try {
                    t = 1, i.send(r, x)
                } catch (w) {
                    if (!(2 > t)) {
                        throw w
                    }
                    x(-1, w)
                }
            } else {
                x(-1, "No Transport")
            }

            function x(a, b, c, d) {
                var j, r, s, u, w, x = b;
                2 !== t && (t = 2, g && clearTimeout(g), i = void 0, f = d || "", v.readyState = a > 0 ? 4 : 0, j = a >= 200 && 300 > a || 304 === a, c && (u = Ob(k, v, c)), u = Pb(k, u, v, j), j ? (k.ifModified && (w = v.getResponseHeader("Last-Modified"), w && (m.lastModified[e] = w), w = v.getResponseHeader("etag"), w && (m.etag[e] = w)), 204 === a || "HEAD" === k.type ? x = "nocontent" : 304 === a ? x = "notmodified" : (x = u.state, r = u.data, s = u.error, j = !s)) : (s = x, (a || !x) && (x = "error", 0 > a && (a = 0))), v.status = a, v.statusText = (b || x) + "", j ? o.resolveWith(l, [r, x, v]) : o.rejectWith(l, [v, x, s]), v.statusCode(q), q = void 0, h && n.trigger(j ? "ajaxSuccess" : "ajaxError", [v, k, j ? r : s]), p.fireWith(l, [v, x]), h && (n.trigger("ajaxComplete", [v, k]), --m.active || m.event.trigger("ajaxStop")))
            }

            return v
        },
        getJSON: function (a, b, c) {
            return m.get(a, b, c, "json")
        },
        getScript: function (a, b) {
            return m.get(a, void 0, b, "script")
        }
    }), m.each(["get", "post"], function (a, b) {
        m[b] = function (a, c, d, e) {
            return m.isFunction(c) && (e = e || d, d = c, c = void 0), m.ajax({
                url: a,
                type: b,
                dataType: e,
                data: c,
                success: d
            })
        }
    }), m._evalUrl = function (a) {
        return m.ajax({url: a, type: "GET", dataType: "script", async: !1, global: !1, "throws": !0})
    }, m.fn.extend({
        wrapAll: function (a) {
            if (m.isFunction(a)) {
                return this.each(function (b) {
                    m(this).wrapAll(a.call(this, b))
                })
            }
            if (this[0]) {
                var b = m(a, this[0].ownerDocument).eq(0).clone(!0);
                this[0].parentNode && b.insertBefore(this[0]), b.map(function () {
                    var a = this;
                    while (a.firstChild && 1 === a.firstChild.nodeType) {
                        a = a.firstChild
                    }
                    return a
                }).append(this)
            }
            return this
        }, wrapInner: function (a) {
            return this.each(m.isFunction(a) ? function (b) {
                m(this).wrapInner(a.call(this, b))
            } : function () {
                var b = m(this), c = b.contents();
                c.length ? c.wrapAll(a) : b.append(a)
            })
        }, wrap: function (a) {
            var b = m.isFunction(a);
            return this.each(function (c) {
                m(this).wrapAll(b ? a.call(this, c) : a)
            })
        }, unwrap: function () {
            return this.parent().each(function () {
                m.nodeName(this, "body") || m(this).replaceWith(this.childNodes)
            }).end()
        }
    }), m.expr.filters.hidden = function (a) {
        return a.offsetWidth <= 0 && a.offsetHeight <= 0 || !k.reliableHiddenOffsets() && "none" === (a.style && a.style.display || m.css(a, "display"))
    }, m.expr.filters.visible = function (a) {
        return !m.expr.filters.hidden(a)
    };
    var Qb = /%20/g, Rb = /\[\]$/, Sb = /\r?\n/g, Tb = /^(?:submit|button|image|reset|file)$/i,
        Ub = /^(?:input|select|textarea|keygen)/i;

    function Vb(a, b, c, d) {
        var e;
        if (m.isArray(b)) {
            m.each(b, function (b, e) {
                c || Rb.test(a) ? d(a, e) : Vb(a + "[" + ("object" == typeof e ? b : "") + "]", e, c, d)
            })
        } else {
            if (c || "object" !== m.type(b)) {
                d(a, b)
            } else {
                for (e in b) {
                    Vb(a + "[" + e + "]", b[e], c, d)
                }
            }
        }
    }

    m.param = function (a, b) {
        var c, d = [], e = function (a, b) {
            b = m.isFunction(b) ? b() : null == b ? "" : b, d[d.length] = encodeURIComponent(a) + "=" + encodeURIComponent(b)
        };
        if (void 0 === b && (b = m.ajaxSettings && m.ajaxSettings.traditional), m.isArray(a) || a.jquery && !m.isPlainObject(a)) {
            m.each(a, function () {
                e(this.name, this.value)
            })
        } else {
            for (c in a) {
                Vb(c, a[c], b, e)
            }
        }
        return d.join("&").replace(Qb, "+")
    }, m.fn.extend({
        serialize: function () {
            return m.param(this.serializeArray())
        }, serializeArray: function () {
            return this.map(function () {
                var a = m.prop(this, "elements");
                return a ? m.makeArray(a) : this
            }).filter(function () {
                var a = this.type;
                return this.name && !m(this).is(":disabled") && Ub.test(this.nodeName) && !Tb.test(a) && (this.checked || !W.test(a))
            }).map(function (a, b) {
                var c = m(this).val();
                return null == c ? null : m.isArray(c) ? m.map(c, function (a) {
                    return {name: b.name, value: a.replace(Sb, "\r\n")}
                }) : {name: b.name, value: c.replace(Sb, "\r\n")}
            }).get()
        }
    }), m.ajaxSettings.xhr = void 0 !== a.ActiveXObject ? function () {
        return !this.isLocal && /^(get|post|head|put|delete|options)$/i.test(this.type) && Zb() || $b()
    } : Zb;
    var Wb = 0, Xb = {}, Yb = m.ajaxSettings.xhr();
    a.attachEvent && a.attachEvent("onunload", function () {
        for (var a in Xb) {
            Xb[a](void 0, !0)
        }
    }), k.cors = !!Yb && "withCredentials" in Yb, Yb = k.ajax = !!Yb, Yb && m.ajaxTransport(function (a) {
        if (!a.crossDomain || k.cors) {
            var b;
            return {
                send: function (c, d) {
                    var e, f = a.xhr(), g = ++Wb;
                    if (f.open(a.type, a.url, a.async, a.username, a.password), a.xhrFields) {
                        for (e in a.xhrFields) {
                            f[e] = a.xhrFields[e]
                        }
                    }
                    a.mimeType && f.overrideMimeType && f.overrideMimeType(a.mimeType), a.crossDomain || c["X-Requested-With"] || (c["X-Requested-With"] = "XMLHttpRequest");
                    for (e in c) {
                        void 0 !== c[e] && f.setRequestHeader(e, c[e] + "")
                    }
                    f.send(a.hasContent && a.data || null), b = function (c, e) {
                        var h, i, j;
                        if (b && (e || 4 === f.readyState)) {
                            if (delete Xb[g], b = void 0, f.onreadystatechange = m.noop, e) {
                                4 !== f.readyState && f.abort()
                            } else {
                                j = {}, h = f.status, "string" == typeof f.responseText && (j.text = f.responseText);
                                try {
                                    i = f.statusText
                                } catch (k) {
                                    i = ""
                                }
                                h || !a.isLocal || a.crossDomain ? 1223 === h && (h = 204) : h = j.text ? 200 : 404
                            }
                        }
                        j && d(h, i, j, f.getAllResponseHeaders())
                    }, a.async ? 4 === f.readyState ? setTimeout(b) : f.onreadystatechange = Xb[g] = b : b()
                }, abort: function () {
                    b && b(void 0, !0)
                }
            }
        }
    });

    function Zb() {
        try {
            return new a.XMLHttpRequest
        } catch (b) {
        }
    }

    function $b() {
        try {
            return new a.ActiveXObject("Microsoft.XMLHTTP")
        } catch (b) {
        }
    }

    m.ajaxSetup({
        accepts: {script: "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript"},
        contents: {script: /(?:java|ecma)script/},
        converters: {
            "text script": function (a) {
                return m.globalEval(a), a
            }
        }
    }), m.ajaxPrefilter("script", function (a) {
        void 0 === a.cache && (a.cache = !1), a.crossDomain && (a.type = "GET", a.global = !1)
    }), m.ajaxTransport("script", function (a) {
        if (a.crossDomain) {
            var b, c = y.head || m("head")[0] || y.documentElement;
            return {
                send: function (d, e) {
                    b = y.createElement("script"), b.async = !0, a.scriptCharset && (b.charset = a.scriptCharset), b.src = a.url, b.onload = b.onreadystatechange = function (a, c) {
                        (c || !b.readyState || /loaded|complete/.test(b.readyState)) && (b.onload = b.onreadystatechange = null, b.parentNode && b.parentNode.removeChild(b), b = null, c || e(200, "success"))
                    }, c.insertBefore(b, c.firstChild)
                }, abort: function () {
                    b && b.onload(void 0, !0)
                }
            }
        }
    });
    var _b = [], ac = /(=)\?(?=&|$)|\?\?/;
    m.ajaxSetup({
        jsonp: "callback", jsonpCallback: function () {
            var a = _b.pop() || m.expando + "_" + vb++;
            return this[a] = !0, a
        }
    }), m.ajaxPrefilter("json jsonp", function (b, c, d) {
        var e, f, g,
            h = b.jsonp !== !1 && (ac.test(b.url) ? "url" : "string" == typeof b.data && !(b.contentType || "").indexOf("application/x-www-form-urlencoded") && ac.test(b.data) && "data");
        return h || "jsonp" === b.dataTypes[0] ? (e = b.jsonpCallback = m.isFunction(b.jsonpCallback) ? b.jsonpCallback() : b.jsonpCallback, h ? b[h] = b[h].replace(ac, "$1" + e) : b.jsonp !== !1 && (b.url += (wb.test(b.url) ? "&" : "?") + b.jsonp + "=" + e), b.converters["script json"] = function () {
            return g || m.error(e + " was not called"), g[0]
        }, b.dataTypes[0] = "json", f = a[e], a[e] = function () {
            g = arguments
        }, d.always(function () {
            a[e] = f, b[e] && (b.jsonpCallback = c.jsonpCallback, _b.push(e)), g && m.isFunction(f) && f(g[0]), g = f = void 0
        }), "script") : void 0
    }), m.parseHTML = function (a, b, c) {
        if (!a || "string" != typeof a) {
            return null
        }
        "boolean" == typeof b && (c = b, b = !1), b = b || y;
        var d = u.exec(a), e = !c && [];
        return d ? [b.createElement(d[1])] : (d = m.buildFragment([a], b, e), e && e.length && m(e).remove(), m.merge([], d.childNodes))
    };
    var bc = m.fn.load;
    m.fn.load = function (a, b, c) {
        if ("string" != typeof a && bc) {
            return bc.apply(this, arguments)
        }
        var d, e, f, g = this, h = a.indexOf(" ");
        return h >= 0 && (d = m.trim(a.slice(h, a.length)), a = a.slice(0, h)), m.isFunction(b) ? (c = b, b = void 0) : b && "object" == typeof b && (f = "POST"), g.length > 0 && m.ajax({
            url: a,
            type: f,
            dataType: "html",
            data: b
        }).done(function (a) {
            e = arguments, g.html(d ? m("<div>").append(m.parseHTML(a)).find(d) : a)
        }).complete(c && function (a, b) {
            g.each(c, e || [a.responseText, b, a])
        }), this
    }, m.each(["ajaxStart", "ajaxStop", "ajaxComplete", "ajaxError", "ajaxSuccess", "ajaxSend"], function (a, b) {
        m.fn[b] = function (a) {
            return this.on(b, a)
        }
    }), m.expr.filters.animated = function (a) {
        return m.grep(m.timers, function (b) {
            return a === b.elem
        }).length
    };
    var cc = a.document.documentElement;

    function dc(a) {
        return m.isWindow(a) ? a : 9 === a.nodeType ? a.defaultView || a.parentWindow : !1
    }

    m.offset = {
        setOffset: function (a, b, c) {
            var d, e, f, g, h, i, j, k = m.css(a, "position"), l = m(a), n = {};
            "static" === k && (a.style.position = "relative"), h = l.offset(), f = m.css(a, "top"), i = m.css(a, "left"), j = ("absolute" === k || "fixed" === k) && m.inArray("auto", [f, i]) > -1, j ? (d = l.position(), g = d.top, e = d.left) : (g = parseFloat(f) || 0, e = parseFloat(i) || 0), m.isFunction(b) && (b = b.call(a, c, h)), null != b.top && (n.top = b.top - h.top + g), null != b.left && (n.left = b.left - h.left + e), "using" in b ? b.using.call(a, n) : l.css(n)
        }
    }, m.fn.extend({
        offset: function (a) {
            if (arguments.length) {
                return void 0 === a ? this : this.each(function (b) {
                    m.offset.setOffset(this, a, b)
                })
            }
            var b, c, d = {top: 0, left: 0}, e = this[0], f = e && e.ownerDocument;
            if (f) {
                return b = f.documentElement, m.contains(b, e) ? (typeof e.getBoundingClientRect !== K && (d = e.getBoundingClientRect()), c = dc(f), {
                    top: d.top + (c.pageYOffset || b.scrollTop) - (b.clientTop || 0),
                    left: d.left + (c.pageXOffset || b.scrollLeft) - (b.clientLeft || 0)
                }) : d
            }
        }, position: function () {
            if (this[0]) {
                var a, b, c = {top: 0, left: 0}, d = this[0];
                return "fixed" === m.css(d, "position") ? b = d.getBoundingClientRect() : (a = this.offsetParent(), b = this.offset(), m.nodeName(a[0], "html") || (c = a.offset()), c.top += m.css(a[0], "borderTopWidth", !0), c.left += m.css(a[0], "borderLeftWidth", !0)), {
                    top: b.top - c.top - m.css(d, "marginTop", !0),
                    left: b.left - c.left - m.css(d, "marginLeft", !0)
                }
            }
        }, offsetParent: function () {
            return this.map(function () {
                var a = this.offsetParent || cc;
                while (a && !m.nodeName(a, "html") && "static" === m.css(a, "position")) {
                    a = a.offsetParent
                }
                return a || cc
            })
        }
    }), m.each({scrollLeft: "pageXOffset", scrollTop: "pageYOffset"}, function (a, b) {
        var c = /Y/.test(b);
        m.fn[a] = function (d) {
            return V(this, function (a, d, e) {
                var f = dc(a);
                return void 0 === e ? f ? b in f ? f[b] : f.document.documentElement[d] : a[d] : void (f ? f.scrollTo(c ? m(f).scrollLeft() : e, c ? e : m(f).scrollTop()) : a[d] = e)
            }, a, d, arguments.length, null)
        }
    }), m.each(["top", "left"], function (a, b) {
        m.cssHooks[b] = La(k.pixelPosition, function (a, c) {
            return c ? (c = Ja(a, b), Ha.test(c) ? m(a).position()[b] + "px" : c) : void 0
        })
    }), m.each({Height: "height", Width: "width"}, function (a, b) {
        m.each({padding: "inner" + a, content: b, "": "outer" + a}, function (c, d) {
            m.fn[d] = function (d, e) {
                var f = arguments.length && (c || "boolean" != typeof d),
                    g = c || (d === !0 || e === !0 ? "margin" : "border");
                return V(this, function (b, c, d) {
                    var e;
                    return m.isWindow(b) ? b.document.documentElement["client" + a] : 9 === b.nodeType ? (e = b.documentElement, Math.max(b.body["scroll" + a], e["scroll" + a], b.body["offset" + a], e["offset" + a], e["client" + a])) : void 0 === d ? m.css(b, c, g) : m.style(b, c, d, g)
                }, b, f ? d : void 0, f, null)
            }
        })
    }), m.fn.size = function () {
        return this.length
    }, m.fn.andSelf = m.fn.addBack, "function" == typeof define && define.amd && define("jquery", [], function () {
        return m
    });
    var ec = a.jQuery, fc = a.$;
    return m.noConflict = function (b) {
        return a.$ === m && (a.$ = fc), b && a.jQuery === m && (a.jQuery = ec), m
    }, typeof b === K && (a.jQuery = a.$ = m), m
});
;
/*!art-template - Template Engine | http://aui.github.com/artTemplate/*/
!function () {
    function X(b) {
        return b.replace(E, "").replace(D, ",").replace(C, "").replace(B, "").replace(A, "").split(/^$|,+/)
    }

    function W(b) {
        return "'" + b.replace(/('|\\)/g, "\\$1").replace(/\r/g, "\\r").replace(/\n/g, "\\n") + "'"
    }

    function V(ap, ao) {
        function an(c) {
            return af += c.split(/\n/).length - 1, ah && (c = c.replace(/[\n\r\t\s]+/g, " ").replace(/<!--.*?-->/g, "")), c && (c = aa[1] + W(c) + aa[2] + "\n"), c
        }

        function am(d) {
            var i = af;
            if (ai ? d = ai(d, ao) : al && (d = d.replace(/\n/g, function () {
                return af++, "$line=" + af + ";"
            })), 0 === d.indexOf("=")) {
                var h = ag && !/^=[=#]/.test(d);
                if (d = d.replace(/^=[=#]?|[\s;]*$/g, ""), h) {
                    var g = d.replace(/\s*\([^\)]+\)/, "");
                    K[g] || /^(include|print)$/.test(g) || (d = "$escape(" + d + ")")
                } else {
                    d = "$string(" + d + ")"
                }
                d = aa[1] + d + aa[2]
            }
            return al && (d = "$line=" + i + ";" + d), G(X(d), function (e) {
                if (e && !ae[e]) {
                    var c;
                    c = "print" === e ? Y : "include" === e ? r : K[e] ? "$utils." + e : J[e] ? "$helpers." + e : "$data." + e, o += e + "=" + c + ",", ae[e] = !0
                }
            }), d + "\n"
        }

        var al = ao.debug, ak = ao.openTag, aj = ao.closeTag, ai = ao.parser, ah = ao.compress, ag = ao.escape, af = 1,
            ae = {$data: 1, $filename: 1, $utils: 1, $helpers: 1, $out: 1, $line: 1}, ac = "".trim,
            aa = ac ? ["$out='';", "$out+=", ";", "$out"] : ["$out=[];", "$out.push(", ");", "$out.join('')"],
            Z = ac ? "$out+=text;return $out;" : "$out.push(text);",
            Y = "function(){var text=''.concat.apply('',arguments);" + Z + "}",
            r = "function(filename,data){data=data||$data;var text=$utils.$include(filename,data,$filename);" + Z + "}",
            o = "'use strict';var $utils=this,$helpers=$utils.$helpers," + (al ? "$line=0," : ""), n = aa[0],
            b = "return new String(" + aa[3] + ");";
        G(ap.split(ak), function (e) {
            e = e.split(aj);
            var d = e[0], f = e[1];
            1 === e.length ? n += an(d) : (n += am(d), f && (n += an(f)))
        });
        var a = o + n + b;
        al && (a = "try{" + a + "}catch(e){throw {filename:$filename,name:'Render Error',message:e.message,line:$line,source:" + W(ap) + ".split(/\\n/)[$line-1].replace(/^[\\s\\t]+/,'')};}");
        try {
            var ad = new Function("$data", "$filename", a);
            return ad.prototype = K, ad
        } catch (ab) {
            throw ab.temp = "function anonymous($data,$filename) {" + a + "}", ab
        }
    }

    var U = function (d, c) {
        return "string" == typeof c ? H(c, {filename: d}) : R(d, c)
    };
    U.version = "3.0.0", U.config = function (d, c) {
        T[d] = c
    };
    var T = U.defaults = {openTag: "<%", closeTag: "%>", escape: !0, cache: !0, compress: !1, parser: null},
        S = U.cache = {};
    U.render = function (d, c) {
        return H(d, c)
    };
    var R = U.renderFile = function (e, d) {
        var f = U.get(e) || I({filename: e, name: "Render Error", message: "Template not found"});
        return d ? f(d) : f
    };
    U.get = function (f) {
        var e;
        if (S[f]) {
            e = S[f]
        } else {
            if ("object" == typeof document) {
                var h = document.getElementById(f);
                if (h) {
                    var g = (h.value || h.innerHTML).replace(/^\s*|\s*$/g, "");
                    e = H(g, {filename: f})
                }
            }
        }
        return e
    };
    var Q = function (d, c) {
        return "string" != typeof d && (c = typeof d, "number" === c ? d += "" : d = "function" === c ? Q(d.call(d)) : ""), d
    }, P = {"<": "&#60;", ">": "&#62;", '"': "&#34;", "'": "&#39;", "&": "&#38;"}, O = function (b) {
        return P[b]
    }, N = function (b) {
        return Q(b).replace(/&(?![\w#]+;)|[<>"']/g, O)
    }, M = Array.isArray || function (b) {
        return "[object Array]" === {}.toString.call(b)
    }, L = function (f, e) {
        var h, g;
        if (M(f)) {
            for (h = 0, g = f.length; g > h; h++) {
                e.call(f, f[h], h, f)
            }
        } else {
            for (h in f) {
                e.call(f, f[h], h)
            }
        }
    }, K = U.utils = {$helpers: {}, $include: R, $string: Q, $escape: N, $each: L};
    U.helper = function (d, c) {
        J[d] = c
    };
    var J = U.helpers = K.$helpers;
    U.onerror = function (e) {
        var d = "Template Error\n\n";
        for (var f in e) {
            d += "<" + f + ">\n" + e[f] + "\n\n"
        }
        "object" == typeof console && console.error(d)
    };
    var I = function (b) {
            return U.onerror(b), function () {
                return "{Template Error}"
            }
        }, H = U.compile = function (e, c) {
            function n(b) {
                try {
                    return new k(b, l) + ""
                } catch (a) {
                    return c.debug ? I(a)() : (c.debug = !0, H(e, c)(b))
                }
            }

            c = c || {};
            for (var m in T) {
                void 0 === c[m] && (c[m] = T[m])
            }
            var l = c.filename;
            try {
                var k = V(e, c)
            } catch (f) {
                return f.filename = l || "anonymous", f.name = "Syntax Error", I(f)
            }
            return n.prototype = k.prototype, n.toString = function () {
                return k.toString()
            }, l && c.cache && (S[l] = n), n
        }, G = K.$each,
        F = "break,case,catch,continue,debugger,default,delete,do,else,false,finally,for,function,if,in,instanceof,new,null,return,switch,this,throw,true,try,typeof,var,void,while,with,abstract,boolean,byte,char,class,const,double,enum,export,extends,final,float,goto,implements,import,int,interface,long,native,package,private,protected,public,short,static,super,synchronized,throws,transient,volatile,arguments,let,yield,undefined",
        E = /\/\*[\w\W]*?\*\/|\/\/[^\n]*\n|\/\/[^\n]*$|"(?:[^"\\]|\\[\w\W])*"|'(?:[^'\\]|\\[\w\W])*'|[\s\t\n]*\.[\s\t\n]*[$\w\.]+/g,
        D = /[^\w$]+/g, C = new RegExp(["\\b" + F.replace(/,/g, "\\b|\\b") + "\\b"].join("|"), "g"),
        B = /^\d[^,]*|,\d[^,]*/g, A = /^,+|,+$/g;
    T.openTag = "{{", T.closeTag = "}}";
    var z = function (g, f) {
        var j = f.split(":"), i = j.shift(), h = j.join(":") || "";
        return h && (h = ", " + h), "$helpers." + i + "(" + g + h + ")"
    };
    T.parser = function (ae, ad) {
        ae = ae.replace(/^\s/, "");
        var ac = ae.split(" "), ab = ac.shift(), aa = ac.join(" ");
        switch (ab) {
            case"if":
                ae = "if(" + aa + "){";
                break;
            case"else":
                ac = "if" === ac.shift() ? " if(" + ac.join(" ") + ")" : "", ae = "}else" + ac + "{";
                break;
            case"/if":
                ae = "}";
                break;
            case"each":
                var Z = ac[0] || "$data", Y = ac[1] || "as", y = ac[2] || "$value", x = ac[3] || "$index",
                    w = y + "," + x;
                "as" !== Y && (Z = "[]"), ae = "$each(" + Z + ",function(" + w + "){";
                break;
            case"/each":
                ae = "});";
                break;
            case"echo":
                ae = "print(" + aa + ");";
                break;
            case"print":
            case"include":
                ae = ab + "(" + ac.join(",") + ");";
                break;
            default:
                if (-1 !== aa.indexOf("|")) {
                    var v = ad.escape;
                    0 === ae.indexOf("#") && (ae = ae.substr(1), v = !1);
                    for (var u = 0, t = ae.split("|"), s = t.length, r = v ? "$escape" : "$string", d = r + "(" + t[u++] + ")"; s > u; u++) {
                        d = z(d, t[u])
                    }
                    ae = "=#" + d
                } else {
                    ae = U.helpers[ab] ? "=#" + ab + "(" + ac.join(",") + ");" : "=" + ae
                }
        }
        return ae
    }, "function" == typeof define ? define(function () {
        return U
    }) : "undefined" != typeof exports ? module.exports = U : this.template = U
}();
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
(function (a) {
    a.fn.unveil = function (h, j) {
        var e = a(window), b = h || 0, d = window.devicePixelRatio > 1, f = d ? "data-image-retina" : "data-image",
            i = this, g;
        this.one("unveil", function () {
            var l = this.getAttribute(f);
            l = l || this.getAttribute("data-image");
            if (l) {
                this.setAttribute("src", l);
                if (typeof j === "function") {
                    j.call(this)
                }
            }
            var k = this.getAttribute("data-url");
            if (k) {
                var m = a(this);
                a.get(k, function (n) {
                    m.html(n)
                })
            }
        });

        function c() {
            var k = i.filter(function () {
                var m = a(this);
                if (m.is(":hidden")) {
                    return
                }
                var l = e.scrollTop(), o = l + e.height(), p = m.offset().top, n = p + m.height();
                return n >= l - b && p <= o + b
            });
            g = k.trigger("unveil");
            i = i.not(g)
        }

        e.on("scroll.unveil resize.unveil lookup.unveil", c);
        c();
        return this
    }
})(window.jQuery || window.Zepto);
;
/*! iScroll v5.2.0-snapshot ~ (c) 2008-2017 Matteo Spinelli ~ http://cubiq.org/license */
(function (d, a, c) {
    var f = d.requestAnimationFrame || d.webkitRequestAnimationFrame || d.mozRequestAnimationFrame || d.oRequestAnimationFrame || d.msRequestAnimationFrame || function (g) {
        d.setTimeout(g, 1000 / 60)
    };
    var b = (function () {
        var k = {};
        var l = a.createElement("div").style;
        var i = (function () {
            var p = ["t", "webkitT", "MozT", "msT", "OT"], n, o = 0, m = p.length;
            for (; o < m; o++) {
                n = p[o] + "ransform";
                if (n in l) {
                    return p[o].substr(0, p[o].length - 1)
                }
            }
            return false
        })();

        function j(m) {
            if (i === false) {
                return false
            }
            if (i === "") {
                return m
            }
            return i + m.charAt(0).toUpperCase() + m.substr(1)
        }

        k.getTime = Date.now || function g() {
            return new Date().getTime()
        };
        k.extend = function (o, n) {
            for (var m in n) {
                o[m] = n[m]
            }
        };
        k.addEvent = function (p, o, n, m) {
            p.addEventListener(o, n, !!m)
        };
        k.removeEvent = function (p, o, n, m) {
            p.removeEventListener(o, n, !!m)
        };
        k.prefixPointerEvent = function (m) {
            return d.MSPointerEvent ? "MSPointer" + m.charAt(7).toUpperCase() + m.substr(8) : m
        };
        k.momentum = function (s, o, p, m, t, u) {
            var n = s - o, q = c.abs(n) / p, v, r;
            u = u === undefined ? 0.0006 : u;
            v = s + (q * q) / (2 * u) * (n < 0 ? -1 : 1);
            r = q / u;
            if (v < m) {
                v = t ? m - (t / 2.5 * (q / 8)) : m;
                n = c.abs(v - s);
                r = n / q
            } else {
                if (v > 0) {
                    v = t ? t / 2.5 * (q / 8) : 0;
                    n = c.abs(s) + v;
                    r = n / q
                }
            }
            return {destination: c.round(v), duration: r}
        };
        var h = j("transform");
        k.extend(k, {
            hasTransform: h !== false,
            hasPerspective: j("perspective") in l,
            hasTouch: "ontouchstart" in d,
            hasPointer: !!(d.PointerEvent || d.MSPointerEvent),
            hasTransition: j("transition") in l
        });
        k.isBadAndroid = (function () {
            var m = d.navigator.appVersion;
            if (/Android/.test(m) && !(/Chrome\/\d/.test(m))) {
                var n = m.match(/Safari\/(\d+.\d)/);
                if (n && typeof n === "object" && n.length >= 2) {
                    return parseFloat(n[1]) < 535.19
                } else {
                    return true
                }
            } else {
                return false
            }
        })();
        k.extend(k.style = {}, {
            transform: h,
            transitionTimingFunction: j("transitionTimingFunction"),
            transitionDuration: j("transitionDuration"),
            transitionDelay: j("transitionDelay"),
            transformOrigin: j("transformOrigin"),
            touchAction: j("touchAction")
        });
        k.hasClass = function (n, o) {
            var m = new RegExp("(^|\\s)" + o + "(\\s|$)");
            return m.test(n.className)
        };
        k.addClass = function (n, o) {
            if (k.hasClass(n, o)) {
                return
            }
            var m = n.className.split(" ");
            m.push(o);
            n.className = m.join(" ")
        };
        k.removeClass = function (n, o) {
            if (!k.hasClass(n, o)) {
                return
            }
            var m = new RegExp("(^|\\s)" + o + "(\\s|$)", "g");
            n.className = n.className.replace(m, " ")
        };
        k.offset = function (m) {
            var o = -m.offsetLeft, n = -m.offsetTop;
            while (m = m.offsetParent) {
                o -= m.offsetLeft;
                n -= m.offsetTop
            }
            return {left: o, top: n}
        };
        k.preventDefaultException = function (o, n) {
            for (var m in n) {
                if (n[m].test(o[m])) {
                    return true
                }
            }
            return false
        };
        k.extend(k.eventType = {}, {
            touchstart: 1,
            touchmove: 1,
            touchend: 1,
            mousedown: 2,
            mousemove: 2,
            mouseup: 2,
            pointerdown: 3,
            pointermove: 3,
            pointerup: 3,
            MSPointerDown: 3,
            MSPointerMove: 3,
            MSPointerUp: 3
        });
        k.extend(k.ease = {}, {
            quadratic: {
                style: "cubic-bezier(0.25, 0.46, 0.45, 0.94)", fn: function (m) {
                    return m * (2 - m)
                }
            }, circular: {
                style: "cubic-bezier(0.1, 0.57, 0.1, 1)", fn: function (m) {
                    return c.sqrt(1 - (--m * m))
                }
            }, back: {
                style: "cubic-bezier(0.175, 0.885, 0.32, 1.275)", fn: function (n) {
                    var m = 4;
                    return (n = n - 1) * n * ((m + 1) * n + m) + 1
                }
            }, bounce: {
                style: "", fn: function (m) {
                    if ((m /= 1) < (1 / 2.75)) {
                        return 7.5625 * m * m
                    } else {
                        if (m < (2 / 2.75)) {
                            return 7.5625 * (m -= (1.5 / 2.75)) * m + 0.75
                        } else {
                            if (m < (2.5 / 2.75)) {
                                return 7.5625 * (m -= (2.25 / 2.75)) * m + 0.9375
                            } else {
                                return 7.5625 * (m -= (2.625 / 2.75)) * m + 0.984375
                            }
                        }
                    }
                }
            }, elastic: {
                style: "", fn: function (m) {
                    var n = 0.22, o = 0.4;
                    if (m === 0) {
                        return 0
                    }
                    if (m == 1) {
                        return 1
                    }
                    return (o * c.pow(2, -10 * m) * c.sin((m - n / 4) * (2 * c.PI) / n) + 1)
                }
            }
        });
        k.tap = function (o, m) {
            var n = a.createEvent("Event");
            n.initEvent(m, true, true);
            n.pageX = o.pageX;
            n.pageY = o.pageY;
            o.target.dispatchEvent(n)
        };
        k.click = function (o) {
            var n = o.target, m;
            if (!(/(SELECT|INPUT|TEXTAREA)/i).test(n.tagName)) {
                m = a.createEvent(d.MouseEvent ? "MouseEvents" : "Event");
                m.initEvent("click", true, true);
                m.view = o.view || d;
                m.detail = 1;
                m.screenX = n.screenX || 0;
                m.screenY = n.screenY || 0;
                m.clientX = n.clientX || 0;
                m.clientY = n.clientY || 0;
                m.ctrlKey = !!o.ctrlKey;
                m.altKey = !!o.altKey;
                m.shiftKey = !!o.shiftKey;
                m.metaKey = !!o.metaKey;
                m.button = 0;
                m.relatedTarget = null;
                m._constructed = true;
                n.dispatchEvent(m)
            }
        };
        k.getTouchAction = function (m, o) {
            var n = "none";
            if (m === "vertical") {
                n = "pan-y"
            } else {
                if (m === "horizontal") {
                    n = "pan-x"
                }
            }
            if (o && n != "none") {
                n += " pinch-zoom"
            }
            return n
        };
        k.getRect = function (m) {
            if (m instanceof SVGElement) {
                var n = m.getBoundingClientRect();
                return {top: n.top, left: n.left, width: n.width, height: n.height}
            } else {
                return {top: m.offsetTop, left: m.offsetLeft, width: m.offsetWidth, height: m.offsetHeight}
            }
        };
        return k
    })();

    function e(j, g) {
        this.wrapper = typeof j == "string" ? a.querySelector(j) : j;
        this.scroller = this.wrapper.children[0];
        this.scrollerStyle = this.scroller.style;
        this.options = {
            disablePointer: !b.hasPointer,
            disableTouch: b.hasPointer || !b.hasTouch,
            disableMouse: b.hasPointer || b.hasTouch,
            startX: 0,
            startY: 0,
            scrollY: true,
            directionLockThreshold: 5,
            momentum: true,
            bounce: true,
            bounceTime: 600,
            bounceEasing: "",
            preventDefault: true,
            preventDefaultException: {tagName: /^(INPUT|TEXTAREA|BUTTON|SELECT)$/},
            HWCompositing: true,
            useTransition: true,
            useTransform: true,
            bindToWrapper: typeof d.onmousedown === "undefined"
        };
        for (var h in g) {
            this.options[h] = g[h]
        }
        this.translateZ = this.options.HWCompositing && b.hasPerspective ? " translateZ(0)" : "";
        this.options.useTransition = b.hasTransition && this.options.useTransition;
        this.options.useTransform = b.hasTransform && this.options.useTransform;
        this.options.eventPassthrough = this.options.eventPassthrough === true ? "vertical" : this.options.eventPassthrough;
        this.options.preventDefault = !this.options.eventPassthrough && this.options.preventDefault;
        this.options.scrollY = this.options.eventPassthrough == "vertical" ? false : this.options.scrollY;
        this.options.scrollX = this.options.eventPassthrough == "horizontal" ? false : this.options.scrollX;
        this.options.freeScroll = this.options.freeScroll && !this.options.eventPassthrough;
        this.options.directionLockThreshold = this.options.eventPassthrough ? 0 : this.options.directionLockThreshold;
        this.options.bounceEasing = typeof this.options.bounceEasing == "string" ? b.ease[this.options.bounceEasing] || b.ease.circular : this.options.bounceEasing;
        this.options.resizePolling = this.options.resizePolling === undefined ? 60 : this.options.resizePolling;
        if (this.options.tap === true) {
            this.options.tap = "tap"
        }
        if (!this.options.useTransition && !this.options.useTransform) {
            if (!(/relative|absolute/i).test(this.scrollerStyle.position)) {
                this.scrollerStyle.position = "relative"
            }
        }
        this.x = 0;
        this.y = 0;
        this.directionX = 0;
        this.directionY = 0;
        this._events = {};
        this._init();
        this.refresh();
        this.scrollTo(this.options.startX, this.options.startY);
        this.enable()
    }

    e.prototype = {
        version: "5.2.0-snapshot", _init: function () {
            this._initEvents()
        }, destroy: function () {
            this._initEvents(true);
            clearTimeout(this.resizeTimeout);
            this.resizeTimeout = null;
            this._execEvent("destroy")
        }, _transitionEnd: function (g) {
            if (g.target != this.scroller || !this.isInTransition) {
                return
            }
            this._transitionTime();
            if (!this.resetPosition(this.options.bounceTime)) {
                this.isInTransition = false;
                this._execEvent("scrollEnd")
            }
        }, _start: function (i) {
            if (b.eventType[i.type] != 1) {
                var h;
                if (!i.which) {
                    h = (i.button < 2) ? 0 : ((i.button == 4) ? 1 : 2)
                } else {
                    h = i.button
                }
                if (h !== 0) {
                    return
                }
            }
            if (!this.enabled || (this.initiated && b.eventType[i.type] !== this.initiated)) {
                return
            }
            if (this.options.preventDefault && !b.isBadAndroid && !b.preventDefaultException(i.target, this.options.preventDefaultException)) {
                i.preventDefault()
            }
            var g = i.touches ? i.touches[0] : i, j;
            this.initiated = b.eventType[i.type];
            this.moved = false;
            this.distX = 0;
            this.distY = 0;
            this.directionX = 0;
            this.directionY = 0;
            this.directionLocked = 0;
            this.startTime = b.getTime();
            if (this.options.useTransition && this.isInTransition) {
                this._transitionTime();
                this.isInTransition = false;
                j = this.getComputedPosition();
                this._translate(c.round(j.x), c.round(j.y));
                this._execEvent("scrollEnd")
            } else {
                if (!this.options.useTransition && this.isAnimating) {
                    this.isAnimating = false;
                    this._execEvent("scrollEnd")
                }
            }
            this.startX = this.x;
            this.startY = this.y;
            this.absStartX = this.x;
            this.absStartY = this.y;
            this.pointX = g.pageX;
            this.pointY = g.pageY;
            this._execEvent("beforeScrollStart")
        }, _move: function (l) {
            if (!this.enabled || b.eventType[l.type] !== this.initiated) {
                return
            }
            if (this.options.preventDefault) {
                l.preventDefault()
            }
            var n = l.touches ? l.touches[0] : l, i = n.pageX - this.pointX, h = n.pageY - this.pointY, m = b.getTime(),
                g, o, k, j;
            this.pointX = n.pageX;
            this.pointY = n.pageY;
            this.distX += i;
            this.distY += h;
            k = c.abs(this.distX);
            j = c.abs(this.distY);
            if (m - this.endTime > 300 && (k < 10 && j < 10)) {
                return
            }
            if (!this.directionLocked && !this.options.freeScroll) {
                if (k > j + this.options.directionLockThreshold) {
                    this.directionLocked = "h"
                } else {
                    if (j >= k + this.options.directionLockThreshold) {
                        this.directionLocked = "v"
                    } else {
                        this.directionLocked = "n"
                    }
                }
            }
            if (this.directionLocked == "h") {
                if (this.options.eventPassthrough == "vertical") {
                    l.preventDefault()
                } else {
                    if (this.options.eventPassthrough == "horizontal") {
                        this.initiated = false;
                        return
                    }
                }
                h = 0
            } else {
                if (this.directionLocked == "v") {
                    if (this.options.eventPassthrough == "horizontal") {
                        l.preventDefault()
                    } else {
                        if (this.options.eventPassthrough == "vertical") {
                            this.initiated = false;
                            return
                        }
                    }
                    i = 0
                }
            }
            i = this.hasHorizontalScroll ? i : 0;
            h = this.hasVerticalScroll ? h : 0;
            g = this.x + i;
            o = this.y + h;
            if (g > 0 || g < this.maxScrollX) {
                g = this.options.bounce ? this.x + i / 3 : g > 0 ? 0 : this.maxScrollX
            }
            if (o > 0 || o < this.maxScrollY) {
                o = this.options.bounce ? this.y + h / 3 : o > 0 ? 0 : this.maxScrollY
            }
            this.directionX = i > 0 ? -1 : i < 0 ? 1 : 0;
            this.directionY = h > 0 ? -1 : h < 0 ? 1 : 0;
            if (!this.moved) {
                this._execEvent("scrollStart")
            }
            this.moved = true;
            this._translate(g, o);
            if (m - this.startTime > 300) {
                this.startTime = m;
                this.startX = this.x;
                this.startY = this.y
            }
        }, _end: function (l) {
            if (!this.enabled || b.eventType[l.type] !== this.initiated) {
                return
            }
            if (this.options.preventDefault && !b.preventDefaultException(l.target, this.options.preventDefaultException)) {
                l.preventDefault()
            }
            var n = l.changedTouches ? l.changedTouches[0] : l, i, h, k = b.getTime() - this.startTime,
                g = c.round(this.x), q = c.round(this.y), p = c.abs(g - this.startX), o = c.abs(q - this.startY), j = 0,
                m = "";
            this.isInTransition = 0;
            this.initiated = 0;
            this.endTime = b.getTime();
            if (this.resetPosition(this.options.bounceTime)) {
                return
            }
            this.scrollTo(g, q);
            if (!this.moved) {
                if (this.options.tap) {
                    b.tap(l, this.options.tap)
                }
                if (this.options.click) {
                    b.click(l)
                }
                this._execEvent("scrollCancel");
                return
            }
            if (this._events.flick && k < 200 && p < 100 && o < 100) {
                this._execEvent("flick");
                return
            }
            if (this.options.momentum && k < 300) {
                i = this.hasHorizontalScroll ? b.momentum(this.x, this.startX, k, this.maxScrollX, this.options.bounce ? this.wrapperWidth : 0, this.options.deceleration) : {
                    destination: g,
                    duration: 0
                };
                h = this.hasVerticalScroll ? b.momentum(this.y, this.startY, k, this.maxScrollY, this.options.bounce ? this.wrapperHeight : 0, this.options.deceleration) : {
                    destination: q,
                    duration: 0
                };
                g = i.destination;
                q = h.destination;
                j = c.max(i.duration, h.duration);
                this.isInTransition = 1
            }
            if (g != this.x || q != this.y) {
                if (g > 0 || g < this.maxScrollX || q > 0 || q < this.maxScrollY) {
                    m = b.ease.quadratic
                }
                this.scrollTo(g, q, j, m);
                return
            }
            this._execEvent("scrollEnd")
        }, _resize: function () {
            var g = this;
            clearTimeout(this.resizeTimeout);
            this.resizeTimeout = setTimeout(function () {
                g.refresh()
            }, this.options.resizePolling)
        }, resetPosition: function (h) {
            var g = this.x, i = this.y;
            h = h || 0;
            if (!this.hasHorizontalScroll || this.x > 0) {
                g = 0
            } else {
                if (this.x < this.maxScrollX) {
                    g = this.maxScrollX
                }
            }
            if (!this.hasVerticalScroll || this.y > 0) {
                i = 0
            } else {
                if (this.y < this.maxScrollY) {
                    i = this.maxScrollY
                }
            }
            if (g == this.x && i == this.y) {
                return false
            }
            this.scrollTo(g, i, h, this.options.bounceEasing);
            return true
        }, disable: function () {
            this.enabled = false
        }, enable: function () {
            this.enabled = true
        }, refresh: function () {
            b.getRect(this.wrapper);
            this.wrapperWidth = this.wrapper.clientWidth;
            this.wrapperHeight = this.wrapper.clientHeight;
            var g = b.getRect(this.scroller);
            this.scrollerWidth = g.width;
            this.scrollerHeight = g.height;
            this.maxScrollX = this.wrapperWidth - this.scrollerWidth;
            this.maxScrollY = this.wrapperHeight - this.scrollerHeight;
            this.hasHorizontalScroll = this.options.scrollX && this.maxScrollX < 0;
            this.hasVerticalScroll = this.options.scrollY && this.maxScrollY < 0;
            if (!this.hasHorizontalScroll) {
                this.maxScrollX = 0;
                this.scrollerWidth = this.wrapperWidth
            }
            if (!this.hasVerticalScroll) {
                this.maxScrollY = 0;
                this.scrollerHeight = this.wrapperHeight
            }
            this.endTime = 0;
            this.directionX = 0;
            this.directionY = 0;
            if (b.hasPointer && !this.options.disablePointer) {
                this.wrapper.style[b.style.touchAction] = b.getTouchAction(this.options.eventPassthrough, true);
                if (!this.wrapper.style[b.style.touchAction]) {
                    this.wrapper.style[b.style.touchAction] = b.getTouchAction(this.options.eventPassthrough, false)
                }
            }
            this.wrapperOffset = b.offset(this.wrapper);
            this._execEvent("refresh");
            this.resetPosition()
        }, on: function (h, g) {
            if (!this._events[h]) {
                this._events[h] = []
            }
            this._events[h].push(g)
        }, off: function (i, h) {
            if (!this._events[i]) {
                return
            }
            var g = this._events[i].indexOf(h);
            if (g > -1) {
                this._events[i].splice(g, 1)
            }
        }, _execEvent: function (j) {
            if (!this._events[j]) {
                return
            }
            var h = 0, g = this._events[j].length;
            if (!g) {
                return
            }
            for (; h < g; h++) {
                this._events[j][h].apply(this, [].slice.call(arguments, 1))
            }
        }, scrollBy: function (g, j, h, i) {
            g = this.x + g;
            j = this.y + j;
            h = h || 0;
            this.scrollTo(g, j, h, i)
        }, scrollTo: function (g, k, i, j) {
            j = j || b.ease.circular;
            this.isInTransition = this.options.useTransition && i > 0;
            var h = this.options.useTransition && j.style;
            if (!i || h) {
                if (h) {
                    this._transitionTimingFunction(j.style);
                    this._transitionTime(i)
                }
                this._translate(g, k)
            } else {
                this._animate(g, k, i, j.fn)
            }
        }, scrollToElement: function (i, k, g, n, m) {
            i = i.nodeType ? i : this.scroller.querySelector(i);
            if (!i) {
                return
            }
            var l = b.offset(i);
            l.left -= this.wrapperOffset.left;
            l.top -= this.wrapperOffset.top;
            var h = b.getRect(i);
            var j = b.getRect(this.wrapper);
            if (g === true) {
                g = c.round(h.width / 2 - j.width / 2)
            }
            if (n === true) {
                n = c.round(h.height / 2 - j.height / 2)
            }
            l.left -= g || 0;
            l.top -= n || 0;
            l.left = l.left > 0 ? 0 : l.left < this.maxScrollX ? this.maxScrollX : l.left;
            l.top = l.top > 0 ? 0 : l.top < this.maxScrollY ? this.maxScrollY : l.top;
            k = k === undefined || k === null || k === "auto" ? c.max(c.abs(this.x - l.left), c.abs(this.y - l.top)) : k;
            this.scrollTo(l.left, l.top, k, m)
        }, _transitionTime: function (i) {
            if (!this.options.useTransition) {
                return
            }
            i = i || 0;
            var g = b.style.transitionDuration;
            if (!g) {
                return
            }
            this.scrollerStyle[g] = i + "ms";
            if (!i && b.isBadAndroid) {
                this.scrollerStyle[g] = "0.0001ms";
                var h = this;
                f(function () {
                    if (h.scrollerStyle[g] === "0.0001ms") {
                        h.scrollerStyle[g] = "0s"
                    }
                })
            }
        }, _transitionTimingFunction: function (g) {
            this.scrollerStyle[b.style.transitionTimingFunction] = g
        }, _translate: function (g, h) {
            if (this.options.useTransform) {
                this.scrollerStyle[b.style.transform] = "translate(" + g + "px," + h + "px)" + this.translateZ
            } else {
                g = c.round(g);
                h = c.round(h);
                this.scrollerStyle.left = g + "px";
                this.scrollerStyle.top = h + "px"
            }
            this.x = g;
            this.y = h
        }, _initEvents: function (g) {
            var h = g ? b.removeEvent : b.addEvent, i = this.options.bindToWrapper ? this.wrapper : d;
            h(d, "orientationchange", this);
            h(d, "resize", this);
            if (this.options.click) {
                h(this.wrapper, "click", this, true)
            }
            if (!this.options.disableMouse) {
                h(this.wrapper, "mousedown", this);
                h(i, "mousemove", this);
                h(i, "mousecancel", this);
                h(i, "mouseup", this)
            }
            if (b.hasPointer && !this.options.disablePointer) {
                h(this.wrapper, b.prefixPointerEvent("pointerdown"), this);
                h(i, b.prefixPointerEvent("pointermove"), this);
                h(i, b.prefixPointerEvent("pointercancel"), this);
                h(i, b.prefixPointerEvent("pointerup"), this)
            }
            if (b.hasTouch && !this.options.disableTouch) {
                h(this.wrapper, "touchstart", this);
                h(i, "touchmove", this);
                h(i, "touchcancel", this);
                h(i, "touchend", this)
            }
            h(this.scroller, "transitionend", this);
            h(this.scroller, "webkitTransitionEnd", this);
            h(this.scroller, "oTransitionEnd", this);
            h(this.scroller, "MSTransitionEnd", this)
        }, getComputedPosition: function () {
            var h = d.getComputedStyle(this.scroller, null), g, i;
            if (this.options.useTransform) {
                h = h[b.style.transform].split(")")[0].split(", ");
                g = +(h[12] || h[4]);
                i = +(h[13] || h[5])
            } else {
                g = +h.left.replace(/[^-\d.]/g, "");
                i = +h.top.replace(/[^-\d.]/g, "")
            }
            return {x: g, y: i}
        }, _animate: function (p, o, j, g) {
            var m = this, l = this.x, k = this.y, h = b.getTime(), n = h + j;

            function i() {
                var q = b.getTime(), s, r, t;
                if (q >= n) {
                    m.isAnimating = false;
                    m._translate(p, o);
                    if (!m.resetPosition(m.options.bounceTime)) {
                        m._execEvent("scrollEnd")
                    }
                    return
                }
                q = (q - h) / j;
                t = g(q);
                s = (p - l) * t + l;
                r = (o - k) * t + k;
                m._translate(s, r);
                if (m.isAnimating) {
                    f(i)
                }
            }

            this.isAnimating = true;
            i()
        }, handleEvent: function (g) {
            switch (g.type) {
                case"touchstart":
                case"pointerdown":
                case"MSPointerDown":
                case"mousedown":
                    this._start(g);
                    break;
                case"touchmove":
                case"pointermove":
                case"MSPointerMove":
                case"mousemove":
                    this._move(g);
                    break;
                case"touchend":
                case"pointerup":
                case"MSPointerUp":
                case"mouseup":
                case"touchcancel":
                case"pointercancel":
                case"MSPointerCancel":
                case"mousecancel":
                    this._end(g);
                    break;
                case"orientationchange":
                case"resize":
                    this._resize();
                    break;
                case"transitionend":
                case"webkitTransitionEnd":
                case"oTransitionEnd":
                case"MSTransitionEnd":
                    this._transitionEnd(g);
                    break;
                case"wheel":
                case"DOMMouseScroll":
                case"mousewheel":
                    this._wheel(g);
                    break;
                case"keydown":
                    this._key(g);
                    break;
                case"click":
                    if (this.enabled && !g._constructed) {
                        g.preventDefault();
                        g.stopPropagation()
                    }
                    break
            }
        }
    };
    e.utils = b;
    if (typeof module != "undefined" && module.exports) {
        module.exports = e
    } else {
        if (typeof define == "function" && define.amd) {
            define(function () {
                return e
            })
        } else {
            d.IScroll = e
        }
    }
})(window, document, Math);
;
var static_base = "/static/nm/js/utils/", timer;
window.uniqueId = 0;
(function ($) {
    $.utilBaseModal = {
        create: function (option) {
            var obj = $(this);
            var options = $.extend({
                type: "pop",
                inAnimate: "",
                outAnimate: "",
                backClose: false,
                scrollLock: false,
                canClose: true,
                remove: true
            }, option);
            if (options.remove == false) {
                if ($("#" + options.type + "-modal-outer").size() > 0) {
                    $("#" + options.type + "-modal-mask").show();
                    $("#" + options.type + "-modal-outer").show();
                    options.inAnimate != "" && $("#" + options.type + "-modal-outer").addClass(options.inAnimate)
                }
            }
            if ($("#" + options.type + "-modal-outer").size() <= 0) {
                var html = "";
                html += '<div id="' + options.type + '-modal-mask" class="modal-mask"></div>    			<div id="' + options.type + '-modal-outer" class="modal-outer animated ' + (options.inAnimate != "" ? options.inAnimate : "") + '">    			<div id="' + options.type + '-modal-inner" class="modal-inner">';
                if (options.canClose == true) {
                    html += '<div id="' + options.type + '-modal-title" class="modal-title">    				<a href="javascript:" id="' + options.type + '-modal-remove" class="modal-remove"><i class="icon icon-close"></i></a>    				</div>'
                }
                html += '<div id="' + options.type + '-modal-content" class="modal-content"></div>    			</div>    			</div>';
                $("body").append(html)
            }
            if (options.scrollLock == true) {
                $("body").data("scroll_position", $(window).scrollTop());
                $("body").css({overflow: "hidden", height: $(window).height()});
                $(window).scrollTop(0)
            }
            $("#" + options.type + "-modal-remove").click(function () {
                $.utilBaseModal.remove(options.type, options);
                return false
            });
            console.log(options.backClose);
            if (options.backClose == true) {
                $("#" + options.type + "-modal-mask").on("click", function () {
                    $("#" + options.type + "-modal-remove").trigger("click");
                    return false
                })
            }
        }, remove: function (type, options) {
            if (options == undefined || options == "") {
                options = {inAnimate: "", outAnimate: "", scrollLock: ""}
            }
            $("#" + type + "-modal-remove").unbind("click");
            if (options.inAnimate != "") {
                $("#" + type + "-modal-outer").removeClass(options.inAnimate)
            }
            if (options.outAnimate != "") {
                $("#" + type + "-modal-outer").addClass(options.outAnimate);
                timer = setTimeout(function () {
                    $("#" + type + "-modal-outer").removeClass(options.inAnimate + " " + options.outAnimate);
                    options.remove == true ? $("#" + type + "-modal-outer").remove() : $("#" + type + "-modal-outer").hide()
                }, 200)
            } else {
                options.remove == true ? $("#" + type + "-modal-outer").remove() : $("#" + type + "-modal-outer").hide()
            }
            options.remove == true ? $("#" + type + "-modal-mask").remove() : $("#" + type + "-modal-mask").hide();
            if (options.scrollLock == true) {
                $("body").css({overflow: "auto", height: "auto"});
                var scroll_position = $("body").data("scroll_position");
                scroll_position && $(window).scrollTop(scroll_position)
            }
            return false
        }
    };
    $.utilAlertModal = {
        ok_button: "确 定",
        cancel_button: "取 消",
        type: "alert",
        download_button: "立即下载",
        wait_button: "稍后下载",
        use_button: "立即使用",
        nouse_button: "稍后使用",
        alert: function (message, callback) {
            $.utilAlertModal.show(message, "alert", function (result) {
                if (callback) {
                    callback(result)
                }
            })
        },
        confirm: function (message, callback) {
            $.utilAlertModal.show(message, "confirm", function (result) {
                if (callback) {
                    callback(result)
                }
            })
        },
        tip: function (message) {
            $.utilAlertModal.show(message, "tip")
        },
        download: function (message, callback) {
            $.utilAlertModal.show(message, "download", function (result) {
                if (callback) {
                    callback(result)
                }
            })
        },
        use: function (message, callback) {
            $.utilAlertModal.show(message, "use", function (result) {
                if (callback) {
                    callback(result)
                }
            })
        },
        remove: function () {
            $("#" + $.utilAlertModal.type + "-modal-remove").trigger("click")
        },
        show: function (msg, mtype, callback) {
            $.utilBaseModal.create({type: $.utilAlertModal.type});
            var html = '<div class="alert-modal-fixed"><div class="alert-modal">				<p class="alert-title">系统提示</p>				<p class="alert-msg">' + msg + '</p>				<p class="alert-buttons"></p>				</div></div>';
            $("#" + $.utilAlertModal.type + "-modal-content").html(html);
            switch (mtype) {
                case"alert":
                    $(".alert-buttons").html('<button type="button" class="alert-ok" style="width:100%;">' + $.utilAlertModal.ok_button + "</button>");
                    $(".alert-ok").on("click", function () {
                        $.utilAlertModal.remove();
                        callback(true);
                        return false
                    });
                    break;
                case"confirm":
                    $(".alert-buttons").html('<button type="button" class="alert-cancel" style="width:50%;">' + $.utilAlertModal.cancel_button + '</button><button type="button" class="alert-ok" style="width:50%;">' + $.utilAlertModal.ok_button + "</button>");
                    $(".alert-ok").on("click", function () {
                        $.utilAlertModal.remove();
                        if (callback) {
                            callback(true)
                        }
                        return false
                    });
                    $(".alert-cancel").on("click", function () {
                        $.utilAlertModal.remove();
                        if (callback) {
                            callback(false)
                        }
                        return false
                    });
                    break;
                case"tip":
                    $(".alert-buttons").remove();
                    $(".alert-msg").css("paddingBottom", "1.5em");
                    break;
                case"download":
                    $(".alert-buttons").html('<button type="button" class="wait-download" style="width:50%;">' + $.utilAlertModal.wait_button + '</button><button type="button" class="download-button" style="width:50%;"><a href="http://t.cn/RLstL36" style="color:#FFF">' + $.utilAlertModal.download_button + "</a></button>");
                    $(".wait-download").on("click", function () {
                        $.utilAlertModal.remove();
                        if (callback) {
                            callback(false)
                        }
                        return false
                    });
                    break;
                case"use":
                    $(".alert-buttons").html('<button type="button" class="alert-ok" style="width:50%"><a href="/page/xinyi">' + $.utilAlertModal.use_button + '</a></button><button type="button" class="alert-cancel" style="width:50%">' + $.utilAlertModal.nouse_button + "</button>");
                    $(".alert-cancel").on("click", function () {
                        $.utilAlertModal.remove();
                        if (callback) {
                            callback(true)
                        }
                        return false
                    });
                    break
            }
            return false
        }
    };
    jAlert = function (message, callback) {
        $.utilAlertModal.alert(message, callback)
    }, jConfirm = function (message, callback) {
        $.utilAlertModal.confirm(message, callback)
    }, jTip = function (message) {
        $.utilAlertModal.tip(message)
    }, jDownload = function (message, callback) {
        $.utilAlertModal.download(message, callback)
    }, jGoto = function (message, callback) {
        $.utilAlertModal.use(message, callback)
    }, $.popModal = function (option) {
        var type = "pop";
        var options = $.extend({url: null, content: null, inAnimate: "slideInUp", outAnimate: "slideOutDown",}, option);
        $.utilBaseModal.create({
            type: type,
            inAnimate: options.inAnimate,
            outAnimate: options.outAnimate,
            scrollLock: true,
        });
        if (options.url) {
            $.get(options.url, function (data) {
                $("#" + type + "-modal-content").html(data)
            })
        }
        if (options.content) {
            $("#" + type + "-modal-content").html(options.content)
        }
    }, $.popModalClose = function () {
        $("#pop-modal-remove").trigger("click");
        return false
    }, $.popupModal = function (option) {
        var type = "popup";
        var options = $.extend({
            url: null,
            content: null,
            canClose: true,
            backClose: false,
            inAnimate: "fadeIn",
            outAnimate: "fadeOut",
        }, option);
        $.utilBaseModal.create({
            type: type,
            canClose: options.canClose,
            backClose: options.backClose,
            inAnimate: options.inAnimate,
            outAnimate: options.outAnimate,
            scrollLock: options.scrollLock
        });
        if (options.url) {
            $.get(options.url, function (data) {
                $("#" + type + "-modal-content").html(data)
            })
        }
        if (options.content) {
            $("#" + type + "-modal-content").html(options.content)
        }
    }, $.popupModalClose = function () {
        $("#popup-modal-remove").trigger("click");
        return false
    }, popupModal = function (option) {
        $.popupModal(option)
    }, popupModalClose = function () {
        $.utilBaseModal.remove("popup", {remove: true, scrollLock: false});
        return false
    }, $.flashTip = function (option) {
        clearTimeout(timer);
        var options = $.extend({position: "top", type: "success", message: "", time: 3000}, option);
        if ($(".flash-tips").size() > 0) {
            $(".flash-tips").attr("class", "flash-tips flash-tips-type-" + options.type).text(options.message)
        } else {
            $("body").append('<div class="flash-tips flash-tips-type-' + options.type + '">' + options.message + "</div>")
        }
        $(".flash-tips").addClass("flash-tips-pos-" + options.position).show();
        timer = setTimeout(function () {
            $(".flash-tips").fadeOut()
        }, options.time)
    }, toast = function (option) {
        $.flashTip(option)
    }, $.D2CMerchantBridge = function (data, hanlename) {
        var u = navigator.userAgent;
        var isAndroid = u.indexOf("Android") > -1 || u.indexOf("Adr") > -1;
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
        if (isAndroid && app_client) {
            try {
                window.WebViewJavascriptBridge.callHandler("d2cinit", data, function (responseData) {
                })
            } catch (err) {
                console.log(err)
            }

            function connectWebViewJavascriptBridge(callback) {
                if (window.WebViewJavascriptBridge) {
                    callback(WebViewJavascriptBridge)
                } else {
                    document.addEventListener("WebViewJavascriptBridgeReady", function () {
                        callback(WebViewJavascriptBridge)
                    }, false)
                }
            }

            connectWebViewJavascriptBridge(function (bridge) {
                if (window.uniqueId == 0) {
                    window.uniqueId = 1;
                    bridge.init(function (message, responseCallback) {
                    })
                }
                bridge.registerHandler("noticeback", function (data, responseCallback) {
                    console.log("回调成功", data);
                    window.respons = data
                });
                bridge.registerHandler("noticeSuback", function (data, responseCallback) {
                    console.log("回调成功", data);
                    window.respons = data
                })
            })
        } else {
            if (isiOS && app_client) {
                window.webkit.messageHandlers.d2cinit.postMessage(data)
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
        var obj_p = e.find(".province");
        var obj_c = e.find(".city");
        var obj_d = e.find(".district");
        if (province.length == 0 || city.length == 0 || district.length == 0) {
            $.getJSON(province_json, function (data) {
                $.each(data, function (i, d) {
                    province[d.code] = d.name
                });
                build_list(province, obj_p.attr("rel"), obj_p, null, 0, default_province)
            });
            setTimeout(function () {
                $.getJSON(city_json, function (data) {
                    $.each(data, function (i, d) {
                        city[d.code] = d.name
                    });
                    build_list(city, obj_c.attr("rel"), obj_c, obj_p, 2, default_city)
                })
            }, 100);
            setTimeout(function () {
                $.getJSON(district_json, function (data) {
                    $.each(data, function (i, d) {
                        district[d.code] = d.name
                    });
                    build_list(district, obj_d.attr("rel"), obj_d, obj_c, 4, default_district)
                })
            }, 200);
            obj_p.change(function () {
                if ($(this).val() == "") {
                    build_list(city, obj_c.attr("rel"), obj_c, obj_p, 2, default_city);
                    build_list(district, obj_d.attr("rel"), obj_d, obj_c, 4, default_district)
                } else {
                    build_list(city, obj_c.attr("rel"), obj_c, obj_p, 2, "");
                    build_list(district, obj_d.attr("rel"), obj_d, obj_c, 4, "")
                }
            });
            obj_c.change(function () {
                build_list(district, obj_d.attr("rel"), obj_d, obj_c, 4, "")
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
    }, $.fn.utilScrollLoad = function (source) {
        var element = $(this), can_load = true, url = element.attr("data-url"), target = element.attr("data-target"),
            template_id = element.attr("template-id");
        var conn_str = (url.indexOf("?") != -1) ? "&" : "?";
        $(window).off("scroll").bind("scroll", function () {
            var page = parseInt(element.attr("data-page")), total = parseInt(element.attr("data-total"));
            if (page < total) {
                if ((parseInt($(window).scrollTop()) + parseInt($(window).height())) > $(document).height() - 100) {
                    loadMorePage()
                }
            } else {
                can_load = false;
                element.html("已经全部加载完了").css("color", "#CCC")
            }
        });
        if (element.offset().top < $(document).height() - 100) {
            loadMorePage()
        }
        element.click(function () {
            loadMorePage()
        });

        function loadMorePage() {
            var page = parseInt(element.attr("data-page")), total = parseInt(element.attr("data-total"));
            if (can_load == true && page < total) {
                element.html("共" + total + "页 正在加载第" + (page + 1) + "页...").css("color", "");
                $.ajax({
                    url: url + conn_str + "p=" + (page + 1) + "&" + new Date().getTime(),
                    dataType: "json",
                    type: "get",
                    beforeSend: function () {
                        can_load = false
                    },
                    success: function (result) {
                        if (result.pager) {
                            var res = result;
                            if (res.pager.next) {
                                can_load = true
                            } else {
                                can_load = false
                            }
                            if (res.pager.list.length > 0) {
                                var html = "";
                                template.config("escape", false);
                                if (template_id) {
                                    html = template(template_id, res)
                                } else {
                                    var render = template.compile(source);
                                    html = render(res)
                                }
                                $("#" + target).append(html);
                                $("#" + target + " img").unveil(300);
                                element.attr("data-page", page + 1).text("点击加载更多").css("color", "");
                                element.attr("data-total", res.pageCount)
                            } else {
                                can_load = false;
                                element.html("已经全部加载完了").css("color", "#CCC")
                            }
                        } else {
                            var res = result.products ? result.products : result.list;
                            if (res.next) {
                                can_load = true
                            } else {
                                can_load = false
                            }
                            if (res.list.length > 0) {
                                var html = "";
                                template.config("escape", false);
                                if (template_id) {
                                    html = template(template_id, res)
                                } else {
                                    var render = template.compile(source);
                                    html = render(res)
                                }
                                $("#" + target).append(html);
                                $("#" + target + " img").unveil(300);
                                element.attr("data-page", page + 1).text("点击加载更多").css("color", "");
                                element.attr("data-total", res.pageCount)
                            } else {
                                can_load = false;
                                element.html("已经全部加载完了").css("color", "#CCC")
                            }
                        }
                    }
                })
            }
        }
    }, $.fn.utilValidateForm = function () {
        var obj_form = $(this), errors = 0;
        if (obj_form.find(".validate").size() > 0) {
            $.each(obj_form.find(".validate"), function (i, o) {
                var message, error = 0;
                var title = $(o).attr("title"), validate_rule = $(o).attr("data-rule"),
                    compare_with = $(o).attr("compare-with"), min_length = $(o).attr("min-length"),
                    max_length = $(o).attr("max-length"), validate_function = $(o).attr("data-function");
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
                    if ($(o).val() == "") {
                        error++;
                        $(o).focus();
                        message = title + "不能为空"
                    }
                }
                if (min_length > 0) {
                    if ($(o).val() != "" && $(o).val().length < min_length) {
                        error++;
                        $(o).focus();
                        message = title + "长度不能小于" + min_length + "位"
                    }
                }
                if (validate_rule == "byte") {
                    var nval = $(o).val().replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "");
                    $(o).val(nval);
                    if ($.utilValidateByteLength(nval) <= 0 || $.utilValidateByteLength(nval) > 30) {
                        error++;
                        $(o).focus();
                        message = title + "长度在15个汉字或者30个字母之内"
                    }
                }
                if (validate_rule == "num") {
                    var val = $(o).val();
                    if (!val.match(/^[\+\-]?\d*?\.?\d*?$/)) {
                        error++;
                        $(o).focus();
                        message = title + "必须为数字"
                    }
                }
                if (max_length > 0) {
                    if ($(o).val().length > max_length) {
                        error++;
                        $(o).focus();
                        message = title + "长度不能大于" + max_length + "位"
                    }
                }
                if (validate_rule == "mobile") {
                    if (!$(o).utilValidateMobile()) {
                        error++;
                        $(o).focus();
                        message = "请输入正确手机号码"
                    }
                }
                if (validate_rule == "email") {
                    if (!$(o).utilValidateEmail()) {
                        error++;
                        $(o).focus();
                        message = "请输入正确邮箱地址"
                    }
                }
                if (compare_with) {
                    if ($(o).val() != $("#" + compare_with).val()) {
                        error++;
                        $(o).focus();
                        message = "两次输入的密码不一致"
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
                    $.flashTip({position: "center", type: "error", message: message});
                    return false
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
        if (re_mobile.test($(this).val())) {
            return true
        } else {
            return false
        }
    }, $.utilValidateByteLength = function (val) {
        var Zhlength = 0;
        var Enlength = 0;
        val = val.replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "");
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
    }, $.utilFormatNumber = function (num, n) {
        var len = num.toString().length;
        while (len < n) {
            num = "0" + num;
            len++
        }
        return num
    }, $.utilFormatCurrency = function (num) {
        if (num) {
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
        } else {
            return null
        }
    }, $.fn.jcMarquees = function (options) {
        var defaults = {marquee: "x", margin_bottom: "0", margin_right: "0", speed: "10"};
        var options = $.extend(defaults, options);
        return this.each(function () {
            var $marquee = $(this), $marquee_scroll = $marquee.children("ul"),
                $marquee_width = $marquee.parent("#Marquee").width();
            $marquee_scroll.append("<li class='clone'></li>");
            $marquee_scroll.find("li").eq(0).children().clone().appendTo(".clone");
            var $marquee_left = $marquee_scroll.find("li");
            if (options.marquee == "x") {
                var x = 0;
                $marquee_scroll.css("width", "4000%");
                $marquee_left.find("div").css({"margin-right": options.margin_right});

                function Marquee_x() {
                    $marquee.scrollLeft(++x);
                    _margin = parseInt($marquee_left.find("div").css("margin-right"));
                    if (x == $marquee_left.width()) {
                        x = 0
                    }
                }

                var MyMar = setInterval(Marquee_x, options.speed)
            }
            if (options.marquee == "y") {
                var y = 0;
                $marquee_scroll.css("height", "4000%");
                $marquee_left.find("div").css("margin-bottom", options.margin_bottom);

                function Marquee_y() {
                    $marquee.scrollTop(++y);
                    _margin = parseInt($marquee_left.find("div").css("margin-bottom"));
                    if (y == $marquee_left.height() + _margin) {
                        y = 0
                    }
                }

                var MyMar = setInterval(Marquee_y, options.speed)
            }
        })
    };
    $.fn.countdown = function () {
        var obj = $(this);
        var timer;
        var type = obj.attr("data-type");
        var defined = obj.attr("data-defined");
        var func = obj.attr("data-function");
        var must = obj.attr("data-musttime");
        var startTime = (new Date(obj.attr("data-startTime"))).getTime();
        var endTime = (new Date(obj.attr("data-endTime"))).getTime();
        var nowTime = (new Date(_server_time)).getTime();
        var st = startTime - nowTime, et = endTime - nowTime;
        var timestr;
        if (type == undefined) {
            type = (st > 0) ? "tostart" : "toend"
        }
        must = must || "";
        var decrease = 1000;
        if (defined == "millisecond") {
            decrease = 100
        }
        timestr = (st > 0) ? st : et;
        clearInterval(timer);
        output();
        timer = setInterval(function () {
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
                    time_tip = "已经结束"
                }
            }
            var time = expireTime(timestr, must);
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

        function expireTime(time, must) {
            var array = new Array();
            if (time > 0) {
                var tDay = Math.floor(time / 86400000);
                if (must) {
                    tHour = Math.floor(time / 3600000)
                } else {
                    tHour = Math.floor((time / 3600000) % 24)
                }
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
            var num = obj.find(".w-star").size();
            var input_num = parseInt(obj.find("input").val());
            if (input_num == NaN) {
                input_num = 0
            }
            if (input_num > 0) {
                for (var n = 4; n >= (5 - input_num); n--) {
                    obj.find(".w-star:eq(" + n + ")").addClass("checked")
                }
            }
            obj.find(".w-star").click(function () {
                var i = $(this).index();
                var obj_p = $(this).parent();
                $(this).siblings("input").val(5 - i);
                obj_p.find(".w-star").removeClass("checked");
                for (var m = 4; m >= i; m--) {
                    obj_p.find(".w-star:eq(" + m + ")").addClass("checked")
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

function userLogin() {
    if (app_client == true) {
        location.href = "/member/login"
    } else {
        location.hash = "#login";
        $.popModal({url: "/member/login?" + new Date().getTime()})
    }
    return false
}

function userbind() {
    location.hash = "#bind";
    $.popModal({url: "/member/bind?" + new Date().getTime()})
}

function closePopModal() {
    $.popModalClose()
}

function LoginDatas() {
    this.message = {handlefunc: "w_login", func: "cessback", type: "login"}
}

function userShare() {
    if (app_client == true) {
        var a = new ShareDatas();
        var b = a.message;
        $.D2CMerchantBridge(b)
    }
}

function noticeFunc() {
    var a = {handlefunc: "w_sign_open", func: "noticeback", type: "page"};
    $.D2CMerchantBridge(a, "noticeback")
}

function openNotice() {
    var a = {handlefunc: "w_notice_open", func: "noticeSuback", type: "page"};
    $.D2CMerchantBridge(a, "noticeSuback")
}

function sendSignStatus() {
    var a = {handlefunc: "w_sign_success", func: "cessback", type: "page"};
    $.D2CMerchantBridge(a)
}

function noticeSuback(a) {
    if (a.hasOwnProperty("open") && a.open == "open") {
        $(".re-button").addClass("on");
        $.flashTip({position: "center", type: "success", message: "签到通知开启"})
    } else {
        $(".re-button").removeClass("on");
        $.flashTip({position: "center", type: "success", message: "签到通知关闭"})
    }
}

function noticeback(a) {
    if (a.hasOwnProperty("open") && a.open == "open") {
        $(".re-button").addClass("on")
    } else {
        $(".re-button").removeClass("on")
    }
}

function ajaxRefresh(a) {
    if (a) {
        if (isExitsFunction("ajaxF")) {
            ajaxF()
        }
    }
}

function ShareDatas() {
    var d = $("share").attr("data-title") || document.title,
        c = $("share").attr("data-desc") || $('meta[name="description"]').attr("content"),
        b = $("share").attr("data-pic") || $("img:eq(0)").attr("src"),
        a = $("share").attr("data-url") || document.location.href;
    this.message = {handlefunc: "w_share", func: "cessback", type: "share", URL: a, desc: c, title: d, pic: b}
}

function showProductBrand() {
    $("body").data("scroll_position", $(window).scrollTop());
    $(".list-sort-designer").toggle();
    $(".arrow-icon").toggleClass("up");
    return false
}

function hideProductBrand() {
    $(".list-sort-designer").hide();
    $(".arrow-icon").removeClass("up");
    var a = $("body").data("scroll_position");
    $(window).scrollTop(a);
    return false
}

function isExitsFunction(funcName) {
    try {
        if (typeof (eval(funcName)) == "function") {
            return true
        }
    } catch (e) {
    }
    return false
}

function countCart() {
    $.ajax({
        url: "/cart/count?t=" + Math.random(), type: "get", data: {}, dataType: "json", success: function (b, a) {
            if (b.cartItemCount > 0) {
                $(".my-cart-num").show();
                $(".my-cart-num").text(b.cartItemCount);
                $(".my-cart-num").data("change", 1)
            }
        }
    })
}

function showServiceQQ() {
    $(".service-qq").show().addClass("bounceIn animated");
    return false
}

function hideServiceQQ() {
    $(".service-qq").addClass("zoomOut");
    setTimeout(function () {
        $(".service-qq").hide().removeClass("bounceIn zoomOut animated")
    }, 1000);
    return false
}

function switchDesignerInfo() {
    $(".designer-info .text").toggle()
}

function interestSuccess(e, c) {
    var d = $("#" + c + "-" + e);
    var b = d.find(".icon").attr("class").replace("-o", "");
    var a = parseInt(d.find("strong").text());
    if (isNaN(a)) {
        a = 0
    }
    d.find(".icon").attr("class", b);
    d.find("strong").text(a + 1);
    d.addClass("disabled");
    return false
}

function voteCount() {
    var b = "", c = "";
    var a = $(".vote-item-num").size();
    $.each($(".vote-item-num"), function (e, f) {
        b += c + $(f).attr("data-id");
        c = (e < a) ? "," : ""
    });
    $.post("/vote/count", {id: b}, function (d) {
        var e = d.result.data;
        $.each(e, function (f, g) {
            $(".vote-item-num[data-id=" + f + "]").text(g)
        })
    }, "json")
}

var voteSuccess = function (b) {
    var a = parseFloat($(".vote-item-num[data-id=" + b + "]:first").text());
    $(".vote-item-num[data-id=" + b + "]").text(a + 1)
};

function installApp() {
    var i = window.location.pathname, l = "";
    i == "/" ? i = "" : l = "S.load=";
    var d = {
        ios: {
            debug: false,
            packageName: "com.d2cmall.buyer",
            packageUrl: "d2cmall://" + i,
            appStoreUrl: "https://itunes.apple.com/app/apple-store/id980211165?pt=117419812&ct=wap&mt=8"
        },
        android: {
            packageName: "com.d2cmall.buyer",
            packageUrl: "d2cmall://" + i,
            fusionPage: "//a.app.qq.com/o/simple.jsp?pkgname=com.d2cmall.buyer",
            downloadUrl: "//app.d2cmall.com/download/d2cmall-install.apk"
        }
    };
    var j = d[isAndroid ? "android" : "ios"]["packageUrl"], b = d[isAndroid ? "android" : "ios"]["packageName"];
    var c = "";
    location.href = j;
    if (isAndroid || iOS) {
        var g, k = 1000, h = true;
        var f = Date.now();
        var e = document.createElement("iframe");
        e.setAttribute("src", j);
        e.setAttribute("style", "display:none");
        document.body.appendChild(e);
        g = setTimeout(function () {
            var a = Date.now();
            if (!f || a - f < k + 10) {
                h = false
            }
        }, k);
        setTimeout(function () {
            if (!h) {
                location.href = "/page/downapp"
            }
            document.body.removeChild(e)
        }, 2000)
    }
    if (c) {
        location.href = c;
        return false
    }
}

function getCouponSuccess(a) {
    if (a == "" || a == undefined) {
        var a = $("body").data("activity_form");
        $(".d2c-register-form").html('<div style="padding:1em 0;text-align:center;line-height:200%;"><p class="icon icon-ok" style="font-size:1.5em;"></p><p>' + a.result.message + "</p></div>");
        return false
    } else {
        $(".coupon-register-form").html('<div style="padding:1em 0;text-align:center;line-height:200%;"><p class="icon icon-ok" style="font-size:1.5em;"></p><p>' + a.result.message + "</p></div>")
    }
}

function activityExcuteSuccess(c, a) {
    if (a.template == "" || a.template == undefined) {
        if ($(".d2c-register-form").size() > 0) {
            c.result.message = a.suctitle ? a.suctitle : c.result.message;
            $(".d2c-register-form").html('<div style="padding:1em 0;text-align:center;line-height:200%;"><p class="icon icon-ok" style="font-size:1.5em;"></p><p>' + c.result.message + "</p></div>")
        } else {
            a.suctitle ? $.flashTip({
                position: "center",
                type: "error",
                message: a.suctitle
            }) : $.flashTip({position: "center", type: "error", message: "奖励已发送至账户，请注意查收"})
        }
    } else {
        var b = template(a.template);
        $("#success-lottery").append(b)
    }
}

function activityExcute(a) {
    closeregistertemp();
    if (a == "" || a == undefined) {
        a = $("body").data("activity_form")
    }
    $.ajax({
        url: a.url, type: "post", data: {mobile: a.mobile}, dataType: "json", success: function (b) {
            if (b.result.status == 1) {
                activityExcuteSuccess(b, a)
            } else {
                $.flashTip({position: "center", type: "error", message: b.result.message})
            }
        }
    })
}

function activityRegister(a) {
    var c = '<div class="coupon-bind" style="z-index:11;position:fixed;top:30%;width:80%;left:5%;background: rgba(255,255,255,0.95);box-shadow: 0 0 10px #333;padding:1rem">			<a href="javascript:" class="modal-remove" id="close-d2c-buttonmessage" style="position:absolute;top:-15px;right:-15px" onclick><i class="icon icon-close"></i></a>			<form class="validate-form form d2c-register-form" action="/member/join/d2c" method="post" call-back="activityExcute" success-tip="false">            <input type="hidden" name="source" value="{{source}}"/><input type="hidden" name="nationCode" class="mobile-code" value="86"/><p style="line-height:2em;text-align:center;" class="coupon-text">{{title}}</p>            <div class="form-item item-flex" style="1px solid #EDEDED">             <label style="line-height:2.8em">手机号</label><input type="text" name="mobile" id="mobile" value="{{mobile}}" title="手机号" data-rule="mobile" class="input validate validate-account" placeholder="请输入手机号"></div> <div class="form-item item-flex" style="1px solid #EDEDED">            <label style="line-height:2.8em">验证码</label><input type="text" name="code" id="code" value="" class="input validate" title="短信校验码" placeholder="请输入短信校验码"><button type="button" data-source="" data-type="Member" class="button button-green validate-send validate-button">获取校验码</button></div>            <div style="text-align:center;padding-top:10px;"><button type="submit" name="coupon-button" id="for-coupon-button" class="button button-red">加入D2C</button></div></form></div>';
    var b = {title: a.title, mobile: a.mobile, source: a.source};
    var e = template.compile(c);
    var d = e(b);
    $("body").append(d);
    return false
}

function closeregistertemp() {
    $(".coupon-bind").remove()
}

$(document).on("click", "#close-d2c-buttonmessage", function () {
    closeregistertemp()
});
$(".promotion-coupon").on("click", function () {
    var a = $(this).attr("data-id");
    $.get("/coupon/batch/" + a, function (b) {
        if (b.result.status == 1) {
            $.flashTip({position: "center", type: "error", message: "领取成功"})
        } else {
            if (b.result.login == false) {
                userLogin();
                return false
            } else {
                $.flashTip({position: "center", type: "error", message: b.result.message})
            }
        }
    }, "json")
});

function changenum(a) {
    switch (a) {
        case 1:
            return "一";
            break;
        case 2:
            return "二";
            break;
        case 3:
            return "三";
            break;
        case 4:
            return "四";
            break;
        case 5:
            return "五";
            break;
        case 6:
            return "六";
            break;
        case 7:
            return "七";
            break;
        case 8:
            return "八";
            break;
        case 9:
            return "九";
            break;
        case 10:
            return "十";
            break;
        case 11:
            return "十一";
            break;
        case 12:
            return "十二";
            break
    }
};
;
window.onerror = function (d, b, a, c, e) {
    console.log("错误信息：", d);
    console.log("出错文件：", b);
    console.log("出错行号：", a);
    console.log("出错列号：", c);
    console.log("错误详情：", e)
};
var userAgent = navigator.userAgent["toLowerCase"](), isiPad = userAgent.match(/ipad/),
    isiPhone = userAgent.match(/iphone|ipod/), iOS = isiPad || isiPhone, isAndroid = userAgent.match(/android/),
    isWeChat = userAgent.match(/micromessenger/);
isTX = userAgent.match(/qq/);
isQQBrowser = userAgent.match(/mqqbrowser/);
isQQ = isTX && !isQQBrowser;
isWeiBo = userAgent.match(/weibo/);
var click_type = iOS ? "touchstart" : "click";
var secretKey = "8811d44df3c0b408f6fa4a31002db44d";
$(function () {
    if (isAndroid) {
        $("html").addClass("platform-android")
    } else {
        if (iOS) {
            $("html").addClass("platform-ios")
        }
    }
    if (isiPhone && (screen.height == 812 && screen.width == 375) && isWeChat) {
        $("html").addClass("iphonex")
    }
    if ($("#product-slide").size() > 0) {
        var mySwiper = new Swiper(".swiper-container", {loop: true, pagination: {el: ".swiper-pagination",}})
    }
    if ($(".my-cart-num").size() > 0) {
        countCart()
    }
    $(".lazyload,.lazyload img").unveil(350);
    if ($(".scroll-load-more").size() > 0) {
        $(".scroll-load-more").utilScrollLoad()
    }
    if ($(".load-brand-product").size() > 0) {
        var source = '<div class="lazyload">			{{each list as value i}}			<a href="/product/{{value.id}}" class="item item-flex item-gap">			<span class="img">			{{if value.store < 1}}<i class="n-product"></i><span class="outp">已售罄</span>{{/if}} <img src="//static.d2c.cn/common/m/img/blank100x157.png" data-image="//img.d2c.cn{{value.productImageCover}}!300" alt="{{value.name}}" /></span>			<span class="title">{{value.name}}</span>			<span class="price"><strong  class="addprice" data-price="{{value.minPrice}}">&yen;{{value.minPrice}}</strong>&nbsp;&nbsp;{{if value.currentPrice< value.originalPrice}}<s>&yen; {{value.originalPrice}}</s>{{/if}}</span>			</a>			{{/each}}			</div>';
        $(".load-brand-product").each(function () {
            var item = this;
            var brand_id = $(item).attr("data-id");
            if (!brand_id) {
                console.log("缺失品牌id")
            }
            $(item).attr("id", "brand-product-" + brand_id);
            $(item).after('<div class="load-more load-more-brand-product" data-id="' + brand_id + '" data-url="/showroom/product/' + brand_id + '" data-target="brand-product-' + brand_id + '" data-page="0" data-total="2">点击加载更多</div>');
            $('.load-more-brand-product[data-id="' + brand_id + '"]').utilScrollLoad(source)
        })
    }
    $(document).on("submit", ".validate-form", function () {
        var form = $(this);
        var url = form.attr("action"), success_tip = form.attr("success-tip"), fail_tip = form.attr("fail-tip"),
            confirm_text = form.attr("confirm"), call_function = form.attr("call-back"),
            redirect_url = form.attr("redirect-url"), target = form.attr("target"),
            is_confirm = $("body").data("is_confirm_form"), no_error = form.attr("error-tips"),
            error_function = form.attr("error-function");
        if (form.utilValidateForm()) {
            if (confirm_text && !is_confirm) {
                jConfirm(confirm_text, function (r) {
                    if (r) {
                        form.find('*[type="submit"]').attr("disabled", true);
                        if (target == undefined) {
                            $.ajax({
                                url: url,
                                data: form.serialize(),
                                type: form.attr("method"),
                                dataType: "json",
                                success: function (data) {
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
                                            success_tip != "" && $.flashTip({
                                                position: "center",
                                                type: "success",
                                                message: success_tip
                                            })
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
                                        $.flashTip({position: "center", type: "error", message: fail_tip});
                                        $(".validate-img").trigger("click")
                                    }
                                    setTimeout(function () {
                                        form.find('*[type="submit"]').removeAttr("disabled")
                                    }, 1000)
                                }
                            })
                        } else {
                            $("body").data("is_confirm_form", true);
                            form.submit();
                            setTimeout(function () {
                                form.find('*[type="submit"]').removeAttr("disabled")
                            }, 1000)
                        }
                    }
                });
                return false
            } else {
                form.find('*[type="submit"]').attr("disabled", true);
                if (target == undefined) {
                    $.ajax({
                        url: url,
                        data: form.serialize(),
                        type: form.attr("method"),
                        dataType: "json",
                        success: function (data) {
                            data = eval(data);
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
                                    success_tip != "" && $.flashTip({
                                        position: "center",
                                        type: "success",
                                        message: success_tip
                                    })
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
                                    }, 2000)
                                }
                            } else {
                                if (!fail_tip) {
                                    fail_tip = data.result.message
                                }
                                if (no_error == "false") {
                                    if (error_function) {
                                        try {
                                            if (typeof (eval(error_function)) == "function") {
                                                eval(error_function + "()")
                                            }
                                        } catch (e) {
                                            alert("不存在" + error_function + "这个函数");
                                            return false
                                        }
                                    }
                                } else {
                                    $.flashTip({position: "center", type: "error", message: fail_tip})
                                }
                                $(".validate-img").trigger("click")
                            }
                            setTimeout(function () {
                                form.find('*[type="submit"]').removeAttr("disabled")
                            }, 1000)
                        }
                    });
                    return false
                } else {
                    console.log(target);
                    $("body").data("is_confirm_form", true);
                    setTimeout(function () {
                        form.find('*[type="submit"]').removeAttr("disabled")
                    }, 1000)
                }
            }
        } else {
            return false
        }
    });
    $(document).on("touchstart", ".ajax-request:not(.disabled)", function () {
        var obj = $(this);
        var obj_id = obj.attr("id"), data_url = obj.attr("data-url"), title = obj.attr("title");
        template_url = obj.attr("template-url"), request_url = obj.attr("request-url"), template_id = obj.attr("template-id"), method_type = obj.attr("method-type"), data_param = obj.attr("data-param"), confirm_text = obj.attr("confirm"), success_tip = obj.attr("success-tip"), fail_tip = obj.attr("fail-tip"), call_function = obj.attr("call-back"), redirect_url = obj.attr("redirect-url");
        if (!method_type) {
            method_type = "get"
        }
        if (!template_url && !template_id) {
            if (confirm_text) {
                jConfirm(confirm_text, function (r) {
                    if (r) {
                        $.ajax({
                            url: data_url,
                            type: method_type,
                            data: data_param ? eval("(" + data_param + ")") : {},
                            dataType: "json",
                            success: function (data) {
                                if (data.result.login == false) {
                                    if (app_client == true) {
                                        $.flashTip({
                                            position: "center",
                                            type: "error",
                                            message: "您还未登录或者登录过期啦，请登录后再操作哦"
                                        })
                                    } else {
                                        $("body").data("function", '$("#' + obj_id + '").trigger("touchstart")');
                                        userLogin()
                                    }
                                    return false
                                }
                                if (data.result.status == 1) {
                                    if (success_tip != "false") {
                                        if (!success_tip) {
                                            success_tip = data.result.message
                                        }
                                        success_tip != "" && $.flashTip({
                                            position: "center",
                                            type: "success",
                                            message: success_tip
                                        })
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
                                    $.flashTip({position: "center", type: "error", message: fail_tip})
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
                            if (app_client == true) {
                                $.flashTip({position: "center", type: "error", message: "您还未登录或者登录过期啦，请登录后再操作哦"})
                            } else {
                                $("body").data("function", '$("#' + obj_id + '").trigger("touchstart")');
                                userLogin()
                            }
                            return false
                        } else {
                            if (!_isD2C) {
                                location.href = "/member/bind"
                            }
                        }
                        if (data.result.status == 1) {
                            if (success_tip != "false") {
                                if (!success_tip) {
                                    success_tip = data.result.message
                                }
                                success_tip != "" && $.flashTip({
                                    position: "center",
                                    type: "success",
                                    message: success_tip
                                })
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
                            $.flashTip({position: "center", type: "error", message: fail_tip})
                        }
                    }
                })
            }
            return false
        } else {
            var data = {};
            if (template_url) {
                $.get(template_url, function (data) {
                    $.popModal({content: data})
                })
            }
            if (request_url) {
                $.getJSON(request_url, function (result) {
                    data = result.result.datas;
                    data.url = data_url;
                    var html = template(template_id, data);
                    $.popModal({content: html})
                })
            }
            if (!request_url && template_id) {
                if (data_param) {
                    data = eval("(" + data_param + ")")
                }
                data.url = data_url;
                var html = template(template_id, data);
                $.popModal({content: html})
            }
            return false
        }
    });
    if ($("a[check-url]").size() > 0) {
        $.each($("a[check-url]"), function (i, o) {
            var url = $(o).attr("check-url");
            if (_memberId != "") {
                $.getJSON(url, function (data) {
                    if (!_isD2C) {
                        location.href = "/member/bind"
                    }
                    if (data.result.status == 1) {
                        var style = $(o).find(".icon").attr("class").replace("-o", "");
                        $(o).find(".icon").attr("class", style);
                        $(o).addClass("disabled")
                    }
                })
            }
        })
    }
    $(document).on("change", ".upload-file", function () {
        var preview_template = '<div class="upload-item" id="{{id}}">            <em class="icon icon-close del"></em>            <span><img src="https://img.d2c.cn{{path}}" width="100%" alt="" /></span>            <input type="hidden" class="path-input" value="{{value}}">        </div>';
        var this_obj = $(this);
        var obj = this_obj.parent();
        var upload_url = this_obj.attr("data-upload-url");
        if ($(".upload-item").size() > 9) {
            jAlert("最多只能上传9张图片。");
            return false
        }
        $.getScript("/static/nm/js/utils/jquery.fileupload.js", function () {
            $.ajaxFileUpload({
                url: upload_url,
                secureuri: false,
                fileObject: this_obj,
                dataType: "json",
                timeout: 3500,
                success: function (data) {
                    if (data.status == 1) {
                        setTimeout(function () {
                            var render = template.compile(preview_template);
                            var html = render(data);
                            obj.before(html)
                        }, 600)
                    }
                }
            })
        })
    });
    $(document).on("touchstart", ".upload-item .del", function () {
        var obj = $(this).parent();
        var evidenceId = obj.attr("id");
        jConfirm("确定要删除该图片吗？", function (r) {
            obj.remove();
            if (evidenceId != "") {
                $.get("/member/delEvidence/" + evidenceId + ".json", function (data) {
                    alert(data)
                })
            }
        });
        return false
    });
    $(document).on("click", ".validate-send:not(.disabled)", function () {
        var this_obj = $(this);
        var account_obj = $(".validate-account");
        var account = account_obj.val();
        var nation_code = $(".mobile-code").val();
        var account_type = account_obj.attr("data-rule");
        var way = this_obj.attr("data-way");
        if (account == "") {
            account_obj.focus();
            return false
        }
        if (account_type == "mobile") {
            if (!account_obj.utilValidateMobile()) {
                account_obj.focus();
                $.flashTip({position: "top", type: "error", message: "请输入正确手机号码"});
                return false
            }
        } else {
            if (!account_obj.utilValidateMobile() && !account_obj.utilValidateEmail()) {
                account_obj.focus();
                $.flashTip({position: "top", type: "error", message: "请输入正确手机号码或邮箱"});
                return false
            }
        }
        var test = function (account) {
            $.ajax({
                url: "/member/checkLoginCode?" + new Date().getTime(),
                type: "post",
                data: {code: account},
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data.result.status == -1) {
                        account = false;
                        $.flashTip({position: "top", type: "error", message: "该手机号已经注册"})
                    }
                }
            });
            return account
        };
        if (way == "register") {
            if (!test(account)) {
                return false
            }
        }
        var type = this_obj.attr("data-type");
        var source = this_obj.attr("data-source");
        var second = 60;
        var sign = hex_md5("mobile=" + account + secretKey);
        var appParams = $.base64("encode", "mobile=" + account + "&sign=" + sign);
        var data = {
            mobile: account,
            source: source,
            type: type,
            terminal: "PC",
            nationCode: nation_code,
            appParams: appParams
        };
        $.ajax({
            url: "/sms/send/encrypt", type: "post", data: data, dataType: "json", success: function (data) {
                if (data.result.status == -2) {
                    $(".validate-img").trigger("click")
                }
                if (data.result.status == 1) {
                    this_obj.attr("disabled", "disabled");
                    this_obj.addClass("disabled");
                    $("#box-modal-remove").trigger("click");
                    var time = setInterval(function () {
                        if (second < 1) {
                            clearInterval(time);
                            this_obj.removeAttr("disabled").text("重发验证码");
                            this_obj.removeClass("disabled")
                        } else {
                            this_obj.text(second + "秒后重发");
                            second--
                        }
                    }, 1000)
                } else {
                    $(this_obj).removeAttr("disabled");
                    $.flashTip({position: "top", type: "error", message: data.result.message})
                }
                return false
            }
        })
    });
    $(document).on("click", ".validate-img", function () {
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
        $(this).attr("src", url + "time=" + new Date().getTime());
        return false
    });
    $(".get-coupon-simple").on(click_type, function () {
        if (!_memberId) {
            userLogin();
            return false
        }
        if (!_isD2C) {
            location.href = "/member/bind";
            return false
        }
        var batchid = $(this).attr("data-batchid"), id = $(this).attr("data-id"), url = "";
        if (batchid) {
            url = "/coupon/batch/" + batchid
        }
        if (id) {
            url = "/coupon/receive/" + id
        }
        $.getJSON(url, function (res) {
            if (res.result.status == 1) {
                jAlert("领取成功~")
            } else {
                toast({position: "center", type: "error", message: "超过领取上限啦~", time: 1500})
            }
        });
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
    if ($(".count-down").size() > 0) {
        $.each($(".count-down"), function (i, d) {
            $(d).countdown()
        })
    }
    $(document).on(click_type, "#choose-country", function () {
        var str = new Date().getTime();
        var country_template = '<header class="modal-header">            <div class="header">            <div class="header-back"><a href="javascript:$.popupModalClose()" class="icon icon-cross"></a></div>            <div class="header-title">选择国家地区</div>        </div>        </header>        <section class="modal-body" id="iScroll-wraper-' + str + '">        <div>            <div class="form">            {{each list as item i}}                <div class="form-item" style="background:transparent;">{{item.group}}</div>                {{each item.list as country ii}}                <div class="form-item item-flex country-item" data-code="{{country.mobileCode}}" data-name="{{country.cnName}}" data-displayname="{{if item.group!=\'常用地区\'}}{{country.enName}} {{/if}}{{country.cnName}}">                    <label style="width:auto;">{{if item.group!=\'常用地区\'}}{{country.enName}} {{/if}}{{country.cnName}}</label>                    <span class="note grey">+{{country.mobileCode}}</span>                </div>                {{/each}}            {{/each}}            </div>        </div>        </section>';
        $.getScript("/static/nm/js/utils/json-country.js", function (result) {
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
            $.popupModal({content: html, inAnimate: "slideInRight", outAnimate: "slideOutRight"});
            new IScroll("#iScroll-wraper-" + str, {mouseWheel: true, click: true})
        });
        return false
    });
    $(document).on("click", ".country-item", function () {
        var obj = $(this);
        var code = obj.attr("data-code"), name = obj.attr("data-name"), displayname = obj.attr("data-displayname");
        $("#country-code").text(displayname);
        $(".choose-country-code").text("+" + code);
        $(".country-code").val(name);
        $(".mobile-code").val(code);
        $.popupModalClose();
        return false
    });
    if ($(".vote-item-num").size() > 0) {
        voteCount()
    }
    window.onhashchange = function () {
        var hashTag = window.location.hash.replace("#", "");
        if (hashTag == "login") {
            userLogin()
        }
        if (hashTag == "") {
            closePopModal()
        }
    };
    if ($(".promotion-productlist-list").size() > 0) {
        $(".promotion-productlist-list").each(function () {
            var obj = $(this);
            var url = obj.attr("data-url");
            if (url) {
                $.get(url, function (data) {
                    obj.append(data);
                    obj.find("img").unveil(350)
                })
            }
        })
    }
    $(document).on("scroll", function () {
        var scroll_top = $(window).scrollTop();
        if (scroll_top > 500) {
            $(".goup").css("display", "block")
        } else {
            $(".goup").css("display", "none")
        }
    });
    $(".goup").on("touchstart", function () {
        $("html, body").animate({scrollTop: 0}, 400)
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
        })
    }
    $(".activity-form-d2c").submit(function () {
        var mobile = $(".use_mobile").val();
        if (mobile != "") {
            var form = $(this);
            $("body").data("activity_form", form.serializeObject());
            $.ajax({
                url: "/member/check",
                type: "get",
                data: form.serialize(),
                dataType: "json",
                success: function (data) {
                    if (data.result.status == 1) {
                        activityExcute(form.serializeObject())
                    } else {
                        activityRegister(form.serializeObject())
                    }
                }
            })
        }
        return false
    })
});
$(window).load(function () {
    var b = location.pathname;
    if (b.indexOf("/page") >= 0) {
        var a = (window.innerWidth > 0) ? window.innerWidth : screen.width;
        $("img").each(function () {
            if ($(this).width() > a) {
                $(this).css("width", "100%")
            }
        })
    }
});
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

function isIE() {
    if (!!window.ActiveXObject || "ActiveXObject" in window) {
        return true
    } else {
        return false
    }
}

function compaerDate(c, a) {
    var d = new Date(c);
    var b = new Date(a);
    if (Date.parse(d) > Date.parse(b)) {
        return 1
    } else {
        if (Date.parse(d) == Date.parse(b)) {
            return 0
        } else {
            if (Date.parse(d) < Date.parse(b)) {
                return -1
            }
        }
    }
}

function parseUrl(c) {
    if (c.indexOf("http") != -1) {
        var b = /^(?:([A-Za-z]+):)?(\/{0,3})([0-9.\-A-Za-z]+)(?::(\d+))?(?:\/([^?#]*))?(?:\?([^#]*))?(?:#(.*))?$/;
        var f = ["url", "scheme", "slash", "host", "port", "path", "query", "hash"]
    } else {
        var b = /^(?:\/([^?#]*))?(?:\?([^#]*))?(?:#(.*))?$/;
        var f = ["url", "path", "query", "hash"]
    }
    var a = b.exec(c);
    var e = {};
    for (var d = 0; d < f.length; d++) {
        e[f[d]] = a[d]
    }
    return e
}

function navigateTo(a) {
    if (a.indexOf("http://m.d2cmall.com") != -1) {
        a = a.replace("http://m.d2cmall.com", "")
    }
    var f = this.parseUrl(a);
    var g = "";
    if (/product\/[0-9]/.test(f.path)) {
        var e = f.path.substr(f.path.lastIndexOf("/") + 1);
        g = "/pages/product/detail?id=" + e
    } else {
        if (f.path.indexOf("product/list") != -1) {
            g = "/pages/product/list" + (f.query ? "?" + f.query : "")
        } else {
            if (f.path.indexOf("product/detail") != -1) {
                g = "/pages/webview/direct?url=" + encodeURIComponent(a)
            } else {
                if (/productComb\/[0-9]/.test(f.path)) {
                    var i = f.path.substr(f.path.lastIndexOf("/") + 1);
                    g = "/pages/product/combined?id=" + i
                } else {
                    if (f.path.indexOf("designer/list") != -1) {
                        g = "/pages/brand/list" + (f.query ? "?" + f.query : "")
                    } else {
                        if (/showroom\/designer\/[0-9]/.test(f.path)) {
                            var j = f.path.substr(f.path.lastIndexOf("/") + 1);
                            g = "/pages/brand/detail?id=" + j
                        } else {
                            if (f.path.indexOf("flashpromotion/product/session") != -1) {
                                g = "/pages/flash/session?type=product"
                            } else {
                                if (f.path.indexOf("flashpromotion/brand/session") != -1) {
                                    g = "/pages/flash/brand?type=brand"
                                } else {
                                    if (/promotion\/[0-9]/.test(f.path)) {
                                        var h = f.path.substr(f.path.lastIndexOf("/") + 1);
                                        g = "/pages/promotion/detail?id=" + h
                                    } else {
                                        if (f.path.indexOf("promotion/items") != -1) {
                                            console.log(f.query);
                                            if (f.query.indexOf("sectionId=") != -1) {
                                                g = "/pages/index/section" + (f.query ? "?" + f.query : "")
                                            } else {
                                                g = "/pages/promotion/list" + (f.query ? "?" + f.query : "")
                                            }
                                        } else {
                                            if (f.path.indexOf("collage/products/list") != -1) {
                                                g = "/pages/groupon/list"
                                            } else {
                                                if (f.path.indexOf("mycollage/list") != -1) {
                                                    g = "/pages/groupon/list?type=mine"
                                                } else {
                                                    if (/collage\/[0-9]/.test(f.path)) {
                                                        var d = f.path.substr(f.path.lastIndexOf("/") + 1);
                                                        g = "/pages/groupon/product?id=" + d
                                                    } else {
                                                        if (f.path.indexOf("coupondef/buynow") != -1) {
                                                            g = "/pages/payment/coupon?" + f.query
                                                        } else {
                                                            if (/coupon\/batch\/[0-9]/.test(f.path)) {
                                                                var b = f.path.substr(f.path.lastIndexOf("/") + 1);
                                                                g = "/pages/coupon/receive?type=batch&id=" + b
                                                            } else {
                                                                if (/shareredpackets\/[0-9]/.test(f.path)) {
                                                                    var c = f.path.substr(f.path.lastIndexOf("/") + 1);
                                                                    g = "/pages/sharepack/detail?id=" + c
                                                                } else {
                                                                    if (/coupon\/[0-9]/.test(f.path)) {
                                                                        var b = f.path.substr(f.path.lastIndexOf("/") + 1);
                                                                        g = "/pages/coupon/receive?type=single&id=" + b
                                                                    } else {
                                                                        if (f.path.indexOf("membershare/list") != -1) {
                                                                            g = "/pages/share/list" + (f.query ? "?" + f.query : "")
                                                                        } else {
                                                                            if (f.path.indexOf("page/physicalStores") != -1) {
                                                                                g = "/pages/store/list"
                                                                            } else {
                                                                                if (f.path.indexOf("bargain/promotion/list") != -1) {
                                                                                    g = "/pages/bargain/index"
                                                                                } else {
                                                                                    g = "/pages/webview/direct?url=" + encodeURIComponent(a)
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    wx.miniProgram.navigateTo({url: g,})
}

if (isWeChat) {
    wx.ready(function () {
        if (window.__wxjs_environment === "miniprogram") {
            $("body").append('<div class="background-fixed"></div>');
            $(document).on("click", "a[href]", function () {
                var b = $(this).attr("href");
                if (b.indexOf("javascript:") == -1 && b.indexOf("#") == -1 && $(this).attr("target") != "self") {
                    navigateTo(b);
                    return false
                }
            });
            if (_partnerId != "" && location.href.indexOf("page") != -1 || location.href.indexOf("promotion") != -1) {
                $("body").append('<div style="position:fixed;top:15px;right:0;z-index:999;">					<div class="copy-button" style="cursor:pointer;padding:.6em 1em;margin-top:1em;border-radius:1.5em 0 0 1.5em;background:rgba(0,0,0,.7);color:#FFF;font-size:.8em;">复制链接</div>				</div>');
                var a = new ClipboardJS(".copy-button", {
                    text: function () {
                        var b = location.href;
                        if (b.indexOf("parent_id") == -1) {
                            b = (b.indexOf("?") != -1) ? (b + "&parent_id=" + _partnerId) : (b + "?parent_id=" + _partnerId)
                        }
                        return b
                    }
                });
                a.on("success", function (b) {
                    toast({position: "center", message: "地址复制成功"})
                })
            }
        }
    })
}

function popupHtml() {
    var a = ['<div class="popup-content">', '<div class="qr-box">', '<p class="box-title">该商品暂只支持APP和小程序购买', '<div style="width:9.375em;margin:0 auto;"><img src="//static.d2c.cn//img/topic/180607/cp/images/pic_wechatcode02@3x.png" width="100%"></div>', '<p class="box-cont-tip">扫码进入D2C小程序<br/>或</p>', '<button class="box-button">下载D2C APP</button>', '<div class="qr-close"><img src="//static.d2c.cn//img/topic/180607/cp/images/icon_closed@3x.png" width="100%"></div>', "</div>", "</div>"].join("");
    popupModal({content: a});
    $("#popup-modal-outer").css("background", "rgba(0,0,0,.5)");
    $(".box-button").on(click_type, function () {
        if (iOS) {
            location.href = "https://itunes.apple.com/us/app/d2c-全球好设计/id980211165?l=zh&ls=1&mt=8 "
        } else {
            if (isAndroid) {
                location.href = "http://a.app.qq.com/o/simple.jsp?pkgname=com.d2cmall.buyer"
            }
        }
    });
    $(".qr-close").on(click_type, function () {
        popupModalClose()
    });
    return false
};
;

;
