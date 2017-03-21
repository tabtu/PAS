package com.sysmon.tab.sysmon;

/**
 * Created by Tab on 2017-03-21.
 */

public class SerMod {
    public int phy_cpu_num;
    public int log_cpu_num;
    public int cpu_num;
    public int DISK_us;
    public int DISK_fr;
    public long MEM_total;
    public long MEM_phy_F;
    public long MEM_cach_F;
    public long MEM_used;
    public double totalWload;
    public double wload1m;
    public double wload5m;
    public double wload15m;
    public double CPUload_us;
    public double CPUload_sy;
    public double CPUload_ni;
    public double CPUload_id;
    public double CPUload_wa;

    public SerMod(String tmp)
    {
        String[] sers = tmp.split(";");
        phy_cpu_num = Integer.parseInt(sers[0]);
        log_cpu_num = Integer.parseInt(sers[1]);
        cpu_num = Integer.parseInt(sers[2]);
        DISK_us = Integer.parseInt(sers[3]);
        DISK_fr = Integer.parseInt(sers[4]);
        MEM_total = Long.parseLong(sers[5]);
        MEM_phy_F = Long.parseLong(sers[6]);
        MEM_cach_F = Long.parseLong(sers[7]);
        MEM_used = Long.parseLong(sers[8]);
        totalWload = Double.parseDouble(sers[9]);
        wload1m = Double.parseDouble(sers[10]);
        wload5m = Double.parseDouble(sers[11]);
        wload15m = Double.parseDouble(sers[12]);
        CPUload_us = Double.parseDouble(sers[13]);
        CPUload_sy = Double.parseDouble(sers[14]);
        CPUload_ni = Double.parseDouble(sers[15]);
        CPUload_id = Double.parseDouble(sers[16]);
        CPUload_wa = Double.parseDouble(sers[17]);
    }

    public SerMod(byte[] tmp)
    {
        phy_cpu_num = (tmp[0] | tmp[1] << 8 | tmp[2] << 16 | tmp[3] << 24);
        log_cpu_num = (tmp[4] | tmp[5] << 8 | tmp[6] << 16 | tmp[7] << 24);
        cpu_num = (tmp[8] | tmp[9] << 8 | tmp[10] << 16 | tmp[11] << 24);
        DISK_us = (tmp[12] | tmp[13] << 8 | tmp[14] << 16 | tmp[15] << 24);
        DISK_fr = (tmp[16] | tmp[17] << 8 | tmp[18] << 16 | tmp[19] << 24);
        MEM_total = (tmp[24] | tmp[25] << 8 | tmp[26] << 16 | tmp[27] << 24 | tmp[28] << 32 | tmp[29] << 40 | tmp[30] << 48 | tmp[31] << 56);
        MEM_phy_F = (tmp[32] | tmp[33] << 8 | tmp[34] << 16 | tmp[35] << 24 | tmp[36] << 32 | tmp[37] << 40 | tmp[38] << 48 | tmp[39] << 56);
        MEM_cach_F = (tmp[40] | tmp[41] << 8 | tmp[42] << 16 | tmp[43] << 24 | tmp[44] << 32 | tmp[45] << 40 | tmp[46] << 48 | tmp[47] << 56);
        MEM_used = (tmp[48] | tmp[49] << 8 | tmp[50] << 16 | tmp[51] << 24 | tmp[52] << 32 | tmp[53] << 40 | tmp[54] << 48 | tmp[55] << 56);
        totalWload = Double.longBitsToDouble(tmp[56] | tmp[57] << 8 | tmp[58] << 16 | tmp[59] << 24 | tmp[60] << 32 | tmp[61] << 40 | tmp[62] << 48 | tmp[63] << 56);
        wload1m = Double.longBitsToDouble(tmp[64] | tmp[65] << 8 | tmp[66] << 16 | tmp[67] << 24 | tmp[68] << 32 | tmp[69] << 40 | tmp[70] << 48 | tmp[71] << 56);
        wload5m = Double.longBitsToDouble(tmp[72] | tmp[73] << 8 | tmp[74] << 16 | tmp[75] << 24 | tmp[76] << 32 | tmp[77] << 40 | tmp[78] << 48 | tmp[79] << 56);
        wload15m = Double.longBitsToDouble(tmp[80] | tmp[81] << 8 | tmp[82] << 16 | tmp[83] << 24 | tmp[84] << 32 | tmp[85] << 40 | tmp[86] << 48 | tmp[87] << 56);
        CPUload_us = Double.longBitsToDouble(tmp[88] | tmp[89] << 8 | tmp[90] << 16 | tmp[91] << 24 | tmp[92] << 32 | tmp[93] << 40 | tmp[94] << 48 | tmp[95] << 56);
        CPUload_sy = Double.longBitsToDouble(tmp[96] | tmp[97] << 8 | tmp[98] << 16 | tmp[99] << 24 | tmp[100] << 32 | tmp[101] << 40 | tmp[102] << 48 | tmp[103] << 56);
        CPUload_ni = Double.longBitsToDouble(tmp[104] | tmp[105] << 8 | tmp[106] << 16 | tmp[107] << 24 | tmp[108] << 32 | tmp[109] << 40 | tmp[110] << 48 | tmp[111] << 56);
        CPUload_id = Double.longBitsToDouble(tmp[112] | tmp[113] << 8 | tmp[114] << 16 | tmp[115] << 24 | tmp[116] << 32 | tmp[117] << 40 | tmp[118] << 48 | tmp[119] << 56);
        CPUload_wa = Double.longBitsToDouble(tmp[120] | tmp[121] << 8 | tmp[122] << 16 | tmp[123] << 24 | tmp[124] << 32 | tmp[125] << 40 | tmp[126] << 48 | tmp[127] << 56);
    }

    public String ToStringExt()
    {
        String result = "";
        result += (phy_cpu_num + ";");
        result += (log_cpu_num + ";");
        result += (cpu_num + ";");
        result += (DISK_us + ";");
        result += (DISK_fr + ";");
        result += (MEM_total + ";");
        result += (MEM_phy_F + ";");
        result += (MEM_cach_F + ";");
        result += (MEM_used + ";");
        result += (totalWload + ";");
        result += (wload1m + ";");
        result += (wload5m + ";");
        result += (wload15m + ";");
        result += (CPUload_us + ";");
        result += (CPUload_sy + ";");
        result += (CPUload_ni + ";");
        result += (CPUload_id + ";");
        result += (CPUload_wa + ";");

        return result;
    }
}
