<?xml version='1.0' encoding='UTF-8'?>
<Project Type="Project" LVVersion="14008000">
	<Item Name="Crio9067" Type="Target Folder">
		<Item Name="NI-cRIO-9067-0307a8d1" Type="RT CompactRIO">
			<Property Name="alias.name" Type="Str">NI-cRIO-9067-0307a8d1</Property>
			<Property Name="alias.value" Type="Str">169.254.88.87</Property>
			<Property Name="CCSymbols" Type="Str">TARGET_TYPE,RT;OS,Linux;CPU,ARM;DeviceCode,7744;</Property>
			<Property Name="crio.ControllerPID" Type="Str">7744</Property>
			<Property Name="host.TargetCPUID" Type="UInt">8</Property>
			<Property Name="host.TargetOSID" Type="UInt">8</Property>
			<Property Name="target.IOScan.Faults" Type="Str"></Property>
			<Property Name="target.IOScan.NetVarPeriod" Type="UInt">100</Property>
			<Property Name="target.IOScan.NetWatchdogEnabled" Type="Bool">false</Property>
			<Property Name="target.IOScan.Period" Type="UInt">10000</Property>
			<Property Name="target.IOScan.PowerupMode" Type="UInt">0</Property>
			<Property Name="target.IOScan.Priority" Type="UInt">0</Property>
			<Property Name="target.IOScan.ReportModeConflict" Type="Bool">true</Property>
			<Property Name="target.RTTarget.VIPath" Type="Path">/home/lvuser/natinst/bin</Property>
			<Item Name="Chassis" Type="cRIO Chassis">
				<Property Name="crio.ProgrammingMode" Type="Str">fpga</Property>
				<Property Name="crio.ResourceID" Type="Str">RIO0</Property>
				<Property Name="crio.Type" Type="Str">cRIO-9067</Property>
				<Item Name="FPGA Target" Type="FPGA Target">
					<Property Name="AutoRun" Type="Bool">false</Property>
					<Property Name="configString.guid" Type="Str">{0AF587D5-DB58-4C01-9734-3232BB0BA002}ResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427E;{0B186824-6930-421A-9956-8F06ADCE149D}resource=/crio_Mod3/AI3;0;ReadMethodType=i16{1BF1A576-E713-4518-9EEA-7C1CC0494A06}"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"{224C9AF9-0294-4695-9004-4C9F079DC735}resource=/crio_Mod3/AI1;0;ReadMethodType=i16{3B6D1A4E-8DA0-42D6-9912-FBC36434E19D}resource=/crio_Mod3/AI2;0;ReadMethodType=i16{472F7FDB-D977-4907-9053-E779F671E821}resource=/Sleep;0;ReadMethodType=bool;WriteMethodType=bool{4CC12055-D469-47FE-93B4-80BEE68F116B}resource=/System Reset;0;ReadMethodType=bool;WriteMethodType=bool{53727201-FC57-424C-ACF1-7FF06B0B9BB6}ArbitrationForOutputData=NeverArbitrate;NumberOfSyncRegistersForOutputData=1;NumberOfSyncRegistersForReadInProject=Auto;resource=/USER FPGA LED;0;ReadMethodType=bool;WriteMethodType=bool{60681DE3-F179-4610-891F-507357654506}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 3,crio.Type=NI 9222[crioConfig.End]{61F4B8D6-8D41-4C55-8943-2DDF8A11422A}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 2,crio.Type=NI 9222[crioConfig.End]{621CE8FD-ED33-4539-8F26-273C93FDA13E}resource=/Chassis Temperature;0;ReadMethodType=i16{8B05AFD0-9DF3-4CD0-88C7-F9D72E4E6449}resource=/crio_Mod1/AI2;0;ReadMethodType=i16{A12D4646-05B5-4EFA-93B2-8A1EF106764D}resource=/crio_Mod2/AI3;0;ReadMethodType=i16{A5D3F63D-55D4-4299-BD32-365C6EDC0E1B}resource=/crio_Mod2/AI2;0;ReadMethodType=i16{AC1144EE-779F-4666-B501-4A6B15FCC384}resource=/crio_Mod2/AI0;0;ReadMethodType=i16{BF136EDF-437A-4F06-B12C-5E22FCB6FB75}resource=/crio_Mod2/AI1;0;ReadMethodType=i16{D62CCF0E-F129-40EB-B129-39E4EF7661FA}resource=/crio_Mod1/AI3;0;ReadMethodType=i16{F1E88F61-822E-4745-9F73-C6D7D27B1E96}resource=/crio_Mod1/AI0;0;ReadMethodType=i16{F4F4C361-713B-4123-A0A4-5342A8EF2E0A}resource=/crio_Mod1/AI1;0;ReadMethodType=i16{F765A35B-7B7F-404C-B3DB-FD04C90F5402}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 1,crio.Type=NI 9222[crioConfig.End]{FCB66385-7FCA-426B-8137-61FB4229EBA5}resource=/crio_Mod3/AI0;0;ReadMethodType=i16{FE4EAAEC-FFE3-4A22-A213-31B9E24A0E9E}resource=/Scan Clock;0;ReadMethodType=boolcRIO-9067/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9067FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA/[rSeriesConfig.Begin][rSeriesConfig.End]</Property>
					<Property Name="configString.name" Type="Str">40 MHz Onboard ClockResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427E;Chassis Temperatureresource=/Chassis Temperature;0;ReadMethodType=i16cRIO-9067/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9067FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA/[rSeriesConfig.Begin][rSeriesConfig.End]FIFO"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"Mod1/AI0resource=/crio_Mod1/AI0;0;ReadMethodType=i16Mod1/AI1resource=/crio_Mod1/AI1;0;ReadMethodType=i16Mod1/AI2resource=/crio_Mod1/AI2;0;ReadMethodType=i16Mod1/AI3resource=/crio_Mod1/AI3;0;ReadMethodType=i16Mod1[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 1,crio.Type=NI 9222[crioConfig.End]Mod2/AI0resource=/crio_Mod2/AI0;0;ReadMethodType=i16Mod2/AI1resource=/crio_Mod2/AI1;0;ReadMethodType=i16Mod2/AI2resource=/crio_Mod2/AI2;0;ReadMethodType=i16Mod2/AI3resource=/crio_Mod2/AI3;0;ReadMethodType=i16Mod2[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 2,crio.Type=NI 9222[crioConfig.End]Mod3/AI0resource=/crio_Mod3/AI0;0;ReadMethodType=i16Mod3/AI1resource=/crio_Mod3/AI1;0;ReadMethodType=i16Mod3/AI2resource=/crio_Mod3/AI2;0;ReadMethodType=i16Mod3/AI3resource=/crio_Mod3/AI3;0;ReadMethodType=i16Mod3[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 3,crio.Type=NI 9222[crioConfig.End]Scan Clockresource=/Scan Clock;0;ReadMethodType=boolSleepresource=/Sleep;0;ReadMethodType=bool;WriteMethodType=boolSystem Resetresource=/System Reset;0;ReadMethodType=bool;WriteMethodType=boolUSER FPGA LEDArbitrationForOutputData=NeverArbitrate;NumberOfSyncRegistersForOutputData=1;NumberOfSyncRegistersForReadInProject=Auto;resource=/USER FPGA LED;0;ReadMethodType=bool;WriteMethodType=bool</Property>
					<Property Name="NI.LV.FPGA.CompileConfigString" Type="Str">cRIO-9067/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9067FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA</Property>
					<Property Name="NI.LV.FPGA.Version" Type="Int">6</Property>
					<Property Name="NI.SortType" Type="Int">3</Property>
					<Property Name="niFpga_TopLevelVIID" Type="Path">/C/PLABuoy/LabView8ch/Crio9067_4chan.vi</Property>
					<Property Name="Resource Name" Type="Str">RIO0</Property>
					<Property Name="Target Class" Type="Str">cRIO-9067</Property>
					<Property Name="Top-Level Timing Source" Type="Str">40 MHz Onboard Clock</Property>
					<Property Name="Top-Level Timing Source Is Default" Type="Bool">true</Property>
					<Item Name="Chassis I/O" Type="Folder">
						<Item Name="Chassis Temperature" Type="Elemental IO">
							<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/Chassis Temperature</Value>
   </Attribute>
</AttributeSet>
</Property>
							<Property Name="FPGA.PersistentID" Type="Str">{621CE8FD-ED33-4539-8F26-273C93FDA13E}</Property>
						</Item>
						<Item Name="Scan Clock" Type="Elemental IO">
							<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/Scan Clock</Value>
   </Attribute>
</AttributeSet>
</Property>
							<Property Name="FPGA.PersistentID" Type="Str">{FE4EAAEC-FFE3-4A22-A213-31B9E24A0E9E}</Property>
						</Item>
						<Item Name="Sleep" Type="Elemental IO">
							<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/Sleep</Value>
   </Attribute>
</AttributeSet>
</Property>
							<Property Name="FPGA.PersistentID" Type="Str">{472F7FDB-D977-4907-9053-E779F671E821}</Property>
						</Item>
						<Item Name="System Reset" Type="Elemental IO">
							<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/System Reset</Value>
   </Attribute>
</AttributeSet>
</Property>
							<Property Name="FPGA.PersistentID" Type="Str">{4CC12055-D469-47FE-93B4-80BEE68F116B}</Property>
						</Item>
						<Item Name="USER FPGA LED" Type="Elemental IO">
							<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="ArbitrationForOutputData">
   <Value>NeverArbitrate</Value>
   </Attribute>
   <Attribute name="NumberOfSyncRegistersForOutputData">
   <Value>1</Value>
   </Attribute>
   <Attribute name="NumberOfSyncRegistersForReadInProject">
   <Value>Auto</Value>
   </Attribute>
   <Attribute name="resource">
   <Value>/USER FPGA LED</Value>
   </Attribute>
</AttributeSet>
</Property>
							<Property Name="FPGA.PersistentID" Type="Str">{53727201-FC57-424C-ACF1-7FF06B0B9BB6}</Property>
						</Item>
					</Item>
					<Item Name="Mod1" Type="Folder">
						<Item Name="Mod1/AI0" Type="Elemental IO">
							<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod1/AI0</Value>
   </Attribute>
</AttributeSet>
</Property>
							<Property Name="FPGA.PersistentID" Type="Str">{F1E88F61-822E-4745-9F73-C6D7D27B1E96}</Property>
						</Item>
						<Item Name="Mod1/AI1" Type="Elemental IO">
							<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod1/AI1</Value>
   </Attribute>
</AttributeSet>
</Property>
							<Property Name="FPGA.PersistentID" Type="Str">{F4F4C361-713B-4123-A0A4-5342A8EF2E0A}</Property>
						</Item>
						<Item Name="Mod1/AI2" Type="Elemental IO">
							<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod1/AI2</Value>
   </Attribute>
</AttributeSet>
</Property>
							<Property Name="FPGA.PersistentID" Type="Str">{8B05AFD0-9DF3-4CD0-88C7-F9D72E4E6449}</Property>
						</Item>
						<Item Name="Mod1/AI3" Type="Elemental IO">
							<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod1/AI3</Value>
   </Attribute>
</AttributeSet>
</Property>
							<Property Name="FPGA.PersistentID" Type="Str">{D62CCF0E-F129-40EB-B129-39E4EF7661FA}</Property>
						</Item>
					</Item>
					<Item Name="Mod2" Type="Folder">
						<Item Name="Mod2/AI0" Type="Elemental IO">
							<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod2/AI0</Value>
   </Attribute>
</AttributeSet>
</Property>
							<Property Name="FPGA.PersistentID" Type="Str">{AC1144EE-779F-4666-B501-4A6B15FCC384}</Property>
						</Item>
						<Item Name="Mod2/AI1" Type="Elemental IO">
							<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod2/AI1</Value>
   </Attribute>
</AttributeSet>
</Property>
							<Property Name="FPGA.PersistentID" Type="Str">{BF136EDF-437A-4F06-B12C-5E22FCB6FB75}</Property>
						</Item>
						<Item Name="Mod2/AI2" Type="Elemental IO">
							<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod2/AI2</Value>
   </Attribute>
</AttributeSet>
</Property>
							<Property Name="FPGA.PersistentID" Type="Str">{A5D3F63D-55D4-4299-BD32-365C6EDC0E1B}</Property>
						</Item>
						<Item Name="Mod2/AI3" Type="Elemental IO">
							<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod2/AI3</Value>
   </Attribute>
</AttributeSet>
</Property>
							<Property Name="FPGA.PersistentID" Type="Str">{A12D4646-05B5-4EFA-93B2-8A1EF106764D}</Property>
						</Item>
					</Item>
					<Item Name="Mod3" Type="Folder">
						<Item Name="Mod3/AI0" Type="Elemental IO">
							<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod3/AI0</Value>
   </Attribute>
</AttributeSet>
</Property>
							<Property Name="FPGA.PersistentID" Type="Str">{FCB66385-7FCA-426B-8137-61FB4229EBA5}</Property>
						</Item>
						<Item Name="Mod3/AI1" Type="Elemental IO">
							<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod3/AI1</Value>
   </Attribute>
</AttributeSet>
</Property>
							<Property Name="FPGA.PersistentID" Type="Str">{224C9AF9-0294-4695-9004-4C9F079DC735}</Property>
						</Item>
						<Item Name="Mod3/AI2" Type="Elemental IO">
							<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod3/AI2</Value>
   </Attribute>
</AttributeSet>
</Property>
							<Property Name="FPGA.PersistentID" Type="Str">{3B6D1A4E-8DA0-42D6-9912-FBC36434E19D}</Property>
						</Item>
						<Item Name="Mod3/AI3" Type="Elemental IO">
							<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod3/AI3</Value>
   </Attribute>
</AttributeSet>
</Property>
							<Property Name="FPGA.PersistentID" Type="Str">{0B186824-6930-421A-9956-8F06ADCE149D}</Property>
						</Item>
					</Item>
					<Item Name="40 MHz Onboard Clock" Type="FPGA Base Clock">
						<Property Name="FPGA.PersistentID" Type="Str">{0AF587D5-DB58-4C01-9734-3232BB0BA002}</Property>
						<Property Name="NI.LV.FPGA.BaseTSConfig" Type="Str">ResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427E;</Property>
						<Property Name="NI.LV.FPGA.BaseTSConfig.Accuracy" Type="Dbl">100</Property>
						<Property Name="NI.LV.FPGA.BaseTSConfig.ClockSignalName" Type="Str">Clk40</Property>
						<Property Name="NI.LV.FPGA.BaseTSConfig.MaxDutyCycle" Type="Dbl">50</Property>
						<Property Name="NI.LV.FPGA.BaseTSConfig.MaxFrequency" Type="Dbl">40000000</Property>
						<Property Name="NI.LV.FPGA.BaseTSConfig.MinDutyCycle" Type="Dbl">50</Property>
						<Property Name="NI.LV.FPGA.BaseTSConfig.MinFrequency" Type="Dbl">40000000</Property>
						<Property Name="NI.LV.FPGA.BaseTSConfig.NominalFrequency" Type="Dbl">40000000</Property>
						<Property Name="NI.LV.FPGA.BaseTSConfig.PeakPeriodJitter" Type="Dbl">250</Property>
						<Property Name="NI.LV.FPGA.BaseTSConfig.ResourceName" Type="Str">40 MHz Onboard Clock</Property>
						<Property Name="NI.LV.FPGA.BaseTSConfig.SupportAndRequireRuntimeEnableDisable" Type="Bool">false</Property>
						<Property Name="NI.LV.FPGA.BaseTSConfig.TopSignalConnect" Type="Str">Clk40</Property>
						<Property Name="NI.LV.FPGA.BaseTSConfig.VariableFrequency" Type="Bool">false</Property>
						<Property Name="NI.LV.FPGA.Valid" Type="Bool">true</Property>
						<Property Name="NI.LV.FPGA.Version" Type="Int">5</Property>
					</Item>
					<Item Name="FIFO" Type="FPGA FIFO">
						<Property Name="Actual Number of Elements" Type="UInt">8191</Property>
						<Property Name="Arbitration for Read" Type="UInt">1</Property>
						<Property Name="Arbitration for Write" Type="UInt">1</Property>
						<Property Name="Control Logic" Type="UInt">0</Property>
						<Property Name="Data Type" Type="UInt">2</Property>
						<Property Name="Disable on Overflow/Underflow" Type="Bool">false</Property>
						<Property Name="fifo.configuration" Type="Str">"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"</Property>
						<Property Name="fifo.configured" Type="Bool">true</Property>
						<Property Name="fifo.projectItemValid" Type="Bool">true</Property>
						<Property Name="fifo.valid" Type="Bool">true</Property>
						<Property Name="fifo.version" Type="Int">12</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{1BF1A576-E713-4518-9EEA-7C1CC0494A06}</Property>
						<Property Name="Local" Type="Bool">false</Property>
						<Property Name="Memory Type" Type="UInt">2</Property>
						<Property Name="Number Of Elements Per Read" Type="UInt">1</Property>
						<Property Name="Number Of Elements Per Write" Type="UInt">1</Property>
						<Property Name="Requested Number of Elements" Type="UInt">4096</Property>
						<Property Name="Type" Type="UInt">2</Property>
						<Property Name="Type Descriptor" Type="Str">100080000000000100094002000349313600010000000000000000</Property>
					</Item>
					<Item Name="Crio9067_4chan.vi" Type="VI" URL="../Crio9067_4chan.vi">
						<Property Name="configString.guid" Type="Str">{0AF587D5-DB58-4C01-9734-3232BB0BA002}ResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427E;{0B186824-6930-421A-9956-8F06ADCE149D}resource=/crio_Mod3/AI3;0;ReadMethodType=i16{1BF1A576-E713-4518-9EEA-7C1CC0494A06}"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"{224C9AF9-0294-4695-9004-4C9F079DC735}resource=/crio_Mod3/AI1;0;ReadMethodType=i16{3B6D1A4E-8DA0-42D6-9912-FBC36434E19D}resource=/crio_Mod3/AI2;0;ReadMethodType=i16{472F7FDB-D977-4907-9053-E779F671E821}resource=/Sleep;0;ReadMethodType=bool;WriteMethodType=bool{4CC12055-D469-47FE-93B4-80BEE68F116B}resource=/System Reset;0;ReadMethodType=bool;WriteMethodType=bool{53727201-FC57-424C-ACF1-7FF06B0B9BB6}ArbitrationForOutputData=NeverArbitrate;NumberOfSyncRegistersForOutputData=1;NumberOfSyncRegistersForReadInProject=Auto;resource=/USER FPGA LED;0;ReadMethodType=bool;WriteMethodType=bool{60681DE3-F179-4610-891F-507357654506}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 3,crio.Type=NI 9222[crioConfig.End]{61F4B8D6-8D41-4C55-8943-2DDF8A11422A}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 2,crio.Type=NI 9222[crioConfig.End]{621CE8FD-ED33-4539-8F26-273C93FDA13E}resource=/Chassis Temperature;0;ReadMethodType=i16{8B05AFD0-9DF3-4CD0-88C7-F9D72E4E6449}resource=/crio_Mod1/AI2;0;ReadMethodType=i16{A12D4646-05B5-4EFA-93B2-8A1EF106764D}resource=/crio_Mod2/AI3;0;ReadMethodType=i16{A5D3F63D-55D4-4299-BD32-365C6EDC0E1B}resource=/crio_Mod2/AI2;0;ReadMethodType=i16{AC1144EE-779F-4666-B501-4A6B15FCC384}resource=/crio_Mod2/AI0;0;ReadMethodType=i16{BF136EDF-437A-4F06-B12C-5E22FCB6FB75}resource=/crio_Mod2/AI1;0;ReadMethodType=i16{D62CCF0E-F129-40EB-B129-39E4EF7661FA}resource=/crio_Mod1/AI3;0;ReadMethodType=i16{F1E88F61-822E-4745-9F73-C6D7D27B1E96}resource=/crio_Mod1/AI0;0;ReadMethodType=i16{F4F4C361-713B-4123-A0A4-5342A8EF2E0A}resource=/crio_Mod1/AI1;0;ReadMethodType=i16{F765A35B-7B7F-404C-B3DB-FD04C90F5402}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 1,crio.Type=NI 9222[crioConfig.End]{FCB66385-7FCA-426B-8137-61FB4229EBA5}resource=/crio_Mod3/AI0;0;ReadMethodType=i16{FE4EAAEC-FFE3-4A22-A213-31B9E24A0E9E}resource=/Scan Clock;0;ReadMethodType=boolcRIO-9067/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9067FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA/[rSeriesConfig.Begin][rSeriesConfig.End]</Property>
						<Property Name="configString.name" Type="Str">40 MHz Onboard ClockResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427E;Chassis Temperatureresource=/Chassis Temperature;0;ReadMethodType=i16cRIO-9067/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9067FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA/[rSeriesConfig.Begin][rSeriesConfig.End]FIFO"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"Mod1/AI0resource=/crio_Mod1/AI0;0;ReadMethodType=i16Mod1/AI1resource=/crio_Mod1/AI1;0;ReadMethodType=i16Mod1/AI2resource=/crio_Mod1/AI2;0;ReadMethodType=i16Mod1/AI3resource=/crio_Mod1/AI3;0;ReadMethodType=i16Mod1[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 1,crio.Type=NI 9222[crioConfig.End]Mod2/AI0resource=/crio_Mod2/AI0;0;ReadMethodType=i16Mod2/AI1resource=/crio_Mod2/AI1;0;ReadMethodType=i16Mod2/AI2resource=/crio_Mod2/AI2;0;ReadMethodType=i16Mod2/AI3resource=/crio_Mod2/AI3;0;ReadMethodType=i16Mod2[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 2,crio.Type=NI 9222[crioConfig.End]Mod3/AI0resource=/crio_Mod3/AI0;0;ReadMethodType=i16Mod3/AI1resource=/crio_Mod3/AI1;0;ReadMethodType=i16Mod3/AI2resource=/crio_Mod3/AI2;0;ReadMethodType=i16Mod3/AI3resource=/crio_Mod3/AI3;0;ReadMethodType=i16Mod3[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 3,crio.Type=NI 9222[crioConfig.End]Scan Clockresource=/Scan Clock;0;ReadMethodType=boolSleepresource=/Sleep;0;ReadMethodType=bool;WriteMethodType=boolSystem Resetresource=/System Reset;0;ReadMethodType=bool;WriteMethodType=boolUSER FPGA LEDArbitrationForOutputData=NeverArbitrate;NumberOfSyncRegistersForOutputData=1;NumberOfSyncRegistersForReadInProject=Auto;resource=/USER FPGA LED;0;ReadMethodType=bool;WriteMethodType=bool</Property>
						<Property Name="NI.LV.FPGA.InterfaceBitfile" Type="Str">C:\PLABuoy\LabView8ch\FPGA Bitfiles\cRioProject1_FPGATarget_Crio90674chan_0--OX-bs750.lvbitx</Property>
					</Item>
					<Item Name="Crio9067_8chan.vi" Type="VI" URL="../Crio9067_8chan.vi">
						<Property Name="configString.guid" Type="Str">{0AF587D5-DB58-4C01-9734-3232BB0BA002}ResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427E;{0B186824-6930-421A-9956-8F06ADCE149D}resource=/crio_Mod3/AI3;0;ReadMethodType=i16{1BF1A576-E713-4518-9EEA-7C1CC0494A06}"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"{224C9AF9-0294-4695-9004-4C9F079DC735}resource=/crio_Mod3/AI1;0;ReadMethodType=i16{3B6D1A4E-8DA0-42D6-9912-FBC36434E19D}resource=/crio_Mod3/AI2;0;ReadMethodType=i16{472F7FDB-D977-4907-9053-E779F671E821}resource=/Sleep;0;ReadMethodType=bool;WriteMethodType=bool{4CC12055-D469-47FE-93B4-80BEE68F116B}resource=/System Reset;0;ReadMethodType=bool;WriteMethodType=bool{53727201-FC57-424C-ACF1-7FF06B0B9BB6}ArbitrationForOutputData=NeverArbitrate;NumberOfSyncRegistersForOutputData=1;NumberOfSyncRegistersForReadInProject=Auto;resource=/USER FPGA LED;0;ReadMethodType=bool;WriteMethodType=bool{60681DE3-F179-4610-891F-507357654506}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 3,crio.Type=NI 9222[crioConfig.End]{61F4B8D6-8D41-4C55-8943-2DDF8A11422A}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 2,crio.Type=NI 9222[crioConfig.End]{621CE8FD-ED33-4539-8F26-273C93FDA13E}resource=/Chassis Temperature;0;ReadMethodType=i16{8B05AFD0-9DF3-4CD0-88C7-F9D72E4E6449}resource=/crio_Mod1/AI2;0;ReadMethodType=i16{A12D4646-05B5-4EFA-93B2-8A1EF106764D}resource=/crio_Mod2/AI3;0;ReadMethodType=i16{A5D3F63D-55D4-4299-BD32-365C6EDC0E1B}resource=/crio_Mod2/AI2;0;ReadMethodType=i16{AC1144EE-779F-4666-B501-4A6B15FCC384}resource=/crio_Mod2/AI0;0;ReadMethodType=i16{BF136EDF-437A-4F06-B12C-5E22FCB6FB75}resource=/crio_Mod2/AI1;0;ReadMethodType=i16{D62CCF0E-F129-40EB-B129-39E4EF7661FA}resource=/crio_Mod1/AI3;0;ReadMethodType=i16{F1E88F61-822E-4745-9F73-C6D7D27B1E96}resource=/crio_Mod1/AI0;0;ReadMethodType=i16{F4F4C361-713B-4123-A0A4-5342A8EF2E0A}resource=/crio_Mod1/AI1;0;ReadMethodType=i16{F765A35B-7B7F-404C-B3DB-FD04C90F5402}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 1,crio.Type=NI 9222[crioConfig.End]{FCB66385-7FCA-426B-8137-61FB4229EBA5}resource=/crio_Mod3/AI0;0;ReadMethodType=i16{FE4EAAEC-FFE3-4A22-A213-31B9E24A0E9E}resource=/Scan Clock;0;ReadMethodType=boolcRIO-9067/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9067FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA/[rSeriesConfig.Begin][rSeriesConfig.End]</Property>
						<Property Name="configString.name" Type="Str">40 MHz Onboard ClockResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427E;Chassis Temperatureresource=/Chassis Temperature;0;ReadMethodType=i16cRIO-9067/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9067FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA/[rSeriesConfig.Begin][rSeriesConfig.End]FIFO"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"Mod1/AI0resource=/crio_Mod1/AI0;0;ReadMethodType=i16Mod1/AI1resource=/crio_Mod1/AI1;0;ReadMethodType=i16Mod1/AI2resource=/crio_Mod1/AI2;0;ReadMethodType=i16Mod1/AI3resource=/crio_Mod1/AI3;0;ReadMethodType=i16Mod1[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 1,crio.Type=NI 9222[crioConfig.End]Mod2/AI0resource=/crio_Mod2/AI0;0;ReadMethodType=i16Mod2/AI1resource=/crio_Mod2/AI1;0;ReadMethodType=i16Mod2/AI2resource=/crio_Mod2/AI2;0;ReadMethodType=i16Mod2/AI3resource=/crio_Mod2/AI3;0;ReadMethodType=i16Mod2[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 2,crio.Type=NI 9222[crioConfig.End]Mod3/AI0resource=/crio_Mod3/AI0;0;ReadMethodType=i16Mod3/AI1resource=/crio_Mod3/AI1;0;ReadMethodType=i16Mod3/AI2resource=/crio_Mod3/AI2;0;ReadMethodType=i16Mod3/AI3resource=/crio_Mod3/AI3;0;ReadMethodType=i16Mod3[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 3,crio.Type=NI 9222[crioConfig.End]Scan Clockresource=/Scan Clock;0;ReadMethodType=boolSleepresource=/Sleep;0;ReadMethodType=bool;WriteMethodType=boolSystem Resetresource=/System Reset;0;ReadMethodType=bool;WriteMethodType=boolUSER FPGA LEDArbitrationForOutputData=NeverArbitrate;NumberOfSyncRegistersForOutputData=1;NumberOfSyncRegistersForReadInProject=Auto;resource=/USER FPGA LED;0;ReadMethodType=bool;WriteMethodType=bool</Property>
						<Property Name="NI.LV.FPGA.InterfaceBitfile" Type="Str">C:\PLABuoy\LabView8ch\FPGA Bitfiles\cRioProject1_FPGATarget_Crio90678chan_8t+xhQj0Qlg.lvbitx</Property>
					</Item>
					<Item Name="Crio9067_12chan.vi" Type="VI" URL="../Crio9067_12chan.vi">
						<Property Name="configString.guid" Type="Str">{0AF587D5-DB58-4C01-9734-3232BB0BA002}ResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427E;{0B186824-6930-421A-9956-8F06ADCE149D}resource=/crio_Mod3/AI3;0;ReadMethodType=i16{1BF1A576-E713-4518-9EEA-7C1CC0494A06}"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"{224C9AF9-0294-4695-9004-4C9F079DC735}resource=/crio_Mod3/AI1;0;ReadMethodType=i16{3B6D1A4E-8DA0-42D6-9912-FBC36434E19D}resource=/crio_Mod3/AI2;0;ReadMethodType=i16{472F7FDB-D977-4907-9053-E779F671E821}resource=/Sleep;0;ReadMethodType=bool;WriteMethodType=bool{4CC12055-D469-47FE-93B4-80BEE68F116B}resource=/System Reset;0;ReadMethodType=bool;WriteMethodType=bool{53727201-FC57-424C-ACF1-7FF06B0B9BB6}ArbitrationForOutputData=NeverArbitrate;NumberOfSyncRegistersForOutputData=1;NumberOfSyncRegistersForReadInProject=Auto;resource=/USER FPGA LED;0;ReadMethodType=bool;WriteMethodType=bool{60681DE3-F179-4610-891F-507357654506}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 3,crio.Type=NI 9222[crioConfig.End]{61F4B8D6-8D41-4C55-8943-2DDF8A11422A}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 2,crio.Type=NI 9222[crioConfig.End]{621CE8FD-ED33-4539-8F26-273C93FDA13E}resource=/Chassis Temperature;0;ReadMethodType=i16{8B05AFD0-9DF3-4CD0-88C7-F9D72E4E6449}resource=/crio_Mod1/AI2;0;ReadMethodType=i16{A12D4646-05B5-4EFA-93B2-8A1EF106764D}resource=/crio_Mod2/AI3;0;ReadMethodType=i16{A5D3F63D-55D4-4299-BD32-365C6EDC0E1B}resource=/crio_Mod2/AI2;0;ReadMethodType=i16{AC1144EE-779F-4666-B501-4A6B15FCC384}resource=/crio_Mod2/AI0;0;ReadMethodType=i16{BF136EDF-437A-4F06-B12C-5E22FCB6FB75}resource=/crio_Mod2/AI1;0;ReadMethodType=i16{D62CCF0E-F129-40EB-B129-39E4EF7661FA}resource=/crio_Mod1/AI3;0;ReadMethodType=i16{F1E88F61-822E-4745-9F73-C6D7D27B1E96}resource=/crio_Mod1/AI0;0;ReadMethodType=i16{F4F4C361-713B-4123-A0A4-5342A8EF2E0A}resource=/crio_Mod1/AI1;0;ReadMethodType=i16{F765A35B-7B7F-404C-B3DB-FD04C90F5402}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 1,crio.Type=NI 9222[crioConfig.End]{FCB66385-7FCA-426B-8137-61FB4229EBA5}resource=/crio_Mod3/AI0;0;ReadMethodType=i16{FE4EAAEC-FFE3-4A22-A213-31B9E24A0E9E}resource=/Scan Clock;0;ReadMethodType=boolcRIO-9067/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9067FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA/[rSeriesConfig.Begin][rSeriesConfig.End]</Property>
						<Property Name="configString.name" Type="Str">40 MHz Onboard ClockResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427E;Chassis Temperatureresource=/Chassis Temperature;0;ReadMethodType=i16cRIO-9067/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9067FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA/[rSeriesConfig.Begin][rSeriesConfig.End]FIFO"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"Mod1/AI0resource=/crio_Mod1/AI0;0;ReadMethodType=i16Mod1/AI1resource=/crio_Mod1/AI1;0;ReadMethodType=i16Mod1/AI2resource=/crio_Mod1/AI2;0;ReadMethodType=i16Mod1/AI3resource=/crio_Mod1/AI3;0;ReadMethodType=i16Mod1[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 1,crio.Type=NI 9222[crioConfig.End]Mod2/AI0resource=/crio_Mod2/AI0;0;ReadMethodType=i16Mod2/AI1resource=/crio_Mod2/AI1;0;ReadMethodType=i16Mod2/AI2resource=/crio_Mod2/AI2;0;ReadMethodType=i16Mod2/AI3resource=/crio_Mod2/AI3;0;ReadMethodType=i16Mod2[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 2,crio.Type=NI 9222[crioConfig.End]Mod3/AI0resource=/crio_Mod3/AI0;0;ReadMethodType=i16Mod3/AI1resource=/crio_Mod3/AI1;0;ReadMethodType=i16Mod3/AI2resource=/crio_Mod3/AI2;0;ReadMethodType=i16Mod3/AI3resource=/crio_Mod3/AI3;0;ReadMethodType=i16Mod3[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 3,crio.Type=NI 9222[crioConfig.End]Scan Clockresource=/Scan Clock;0;ReadMethodType=boolSleepresource=/Sleep;0;ReadMethodType=bool;WriteMethodType=boolSystem Resetresource=/System Reset;0;ReadMethodType=bool;WriteMethodType=boolUSER FPGA LEDArbitrationForOutputData=NeverArbitrate;NumberOfSyncRegistersForOutputData=1;NumberOfSyncRegistersForReadInProject=Auto;resource=/USER FPGA LED;0;ReadMethodType=bool;WriteMethodType=bool</Property>
						<Property Name="NI.LV.FPGA.InterfaceBitfile" Type="Str">C:\PLABuoy\LabView8ch\FPGA Bitfiles\cRioProject1_FPGATarget_Crio906712chan_2xe3Gr76Oa0.lvbitx</Property>
					</Item>
					<Item Name="IP Builder" Type="IP Builder Target">
						<Item Name="Dependencies" Type="Dependencies"/>
						<Item Name="Build Specifications" Type="Build"/>
					</Item>
					<Item Name="Mod1" Type="RIO C Series Module">
						<Property Name="crio.Calibration" Type="Str">0</Property>
						<Property Name="crio.Location" Type="Str">Slot 1</Property>
						<Property Name="crio.RequiresValidation" Type="Bool">false</Property>
						<Property Name="crio.SDcounterSlaveChannelMask" Type="Str">0</Property>
						<Property Name="crio.SDCounterSlaveMasterSlot" Type="Str">0</Property>
						<Property Name="crio.SDInputFilter" Type="Str">128</Property>
						<Property Name="crio.SupportsDynamicRes" Type="Bool">false</Property>
						<Property Name="crio.Type" Type="Str">NI 9222</Property>
						<Property Name="cRIOModule.DigitalIOMode" Type="Str">0</Property>
						<Property Name="cRIOModule.EnableSpecialtyDigital" Type="Str">false</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{F765A35B-7B7F-404C-B3DB-FD04C90F5402}</Property>
					</Item>
					<Item Name="Mod2" Type="RIO C Series Module">
						<Property Name="crio.Calibration" Type="Str">0</Property>
						<Property Name="crio.Location" Type="Str">Slot 2</Property>
						<Property Name="crio.RequiresValidation" Type="Bool">false</Property>
						<Property Name="crio.SDcounterSlaveChannelMask" Type="Str">0</Property>
						<Property Name="crio.SDCounterSlaveMasterSlot" Type="Str">0</Property>
						<Property Name="crio.SDInputFilter" Type="Str">128</Property>
						<Property Name="crio.SupportsDynamicRes" Type="Bool">false</Property>
						<Property Name="crio.Type" Type="Str">NI 9222</Property>
						<Property Name="cRIOModule.DigitalIOMode" Type="Str">0</Property>
						<Property Name="cRIOModule.EnableSpecialtyDigital" Type="Str">false</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{61F4B8D6-8D41-4C55-8943-2DDF8A11422A}</Property>
					</Item>
					<Item Name="Mod3" Type="RIO C Series Module">
						<Property Name="crio.Calibration" Type="Str">0</Property>
						<Property Name="crio.Location" Type="Str">Slot 3</Property>
						<Property Name="crio.RequiresValidation" Type="Bool">false</Property>
						<Property Name="crio.SDcounterSlaveChannelMask" Type="Str">0</Property>
						<Property Name="crio.SDCounterSlaveMasterSlot" Type="Str">0</Property>
						<Property Name="crio.SDInputFilter" Type="Str">128</Property>
						<Property Name="crio.SupportsDynamicRes" Type="Bool">false</Property>
						<Property Name="crio.Type" Type="Str">NI 9222</Property>
						<Property Name="cRIOModule.DigitalIOMode" Type="Str">0</Property>
						<Property Name="cRIOModule.EnableSpecialtyDigital" Type="Str">false</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{60681DE3-F179-4610-891F-507357654506}</Property>
					</Item>
					<Item Name="LED Button.ctl" Type="VI" URL="../LED Button.ctl">
						<Property Name="configString.guid" Type="Str">{0AF587D5-DB58-4C01-9734-3232BB0BA002}ResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427E;{0B186824-6930-421A-9956-8F06ADCE149D}resource=/crio_Mod3/AI3;0;ReadMethodType=i16{1BF1A576-E713-4518-9EEA-7C1CC0494A06}"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"{224C9AF9-0294-4695-9004-4C9F079DC735}resource=/crio_Mod3/AI1;0;ReadMethodType=i16{3B6D1A4E-8DA0-42D6-9912-FBC36434E19D}resource=/crio_Mod3/AI2;0;ReadMethodType=i16{472F7FDB-D977-4907-9053-E779F671E821}resource=/Sleep;0;ReadMethodType=bool;WriteMethodType=bool{4CC12055-D469-47FE-93B4-80BEE68F116B}resource=/System Reset;0;ReadMethodType=bool;WriteMethodType=bool{53727201-FC57-424C-ACF1-7FF06B0B9BB6}ArbitrationForOutputData=NeverArbitrate;NumberOfSyncRegistersForOutputData=1;NumberOfSyncRegistersForReadInProject=Auto;resource=/USER FPGA LED;0;ReadMethodType=bool;WriteMethodType=bool{60681DE3-F179-4610-891F-507357654506}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 3,crio.Type=NI 9222[crioConfig.End]{61F4B8D6-8D41-4C55-8943-2DDF8A11422A}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 2,crio.Type=NI 9222[crioConfig.End]{621CE8FD-ED33-4539-8F26-273C93FDA13E}resource=/Chassis Temperature;0;ReadMethodType=i16{8B05AFD0-9DF3-4CD0-88C7-F9D72E4E6449}resource=/crio_Mod1/AI2;0;ReadMethodType=i16{A12D4646-05B5-4EFA-93B2-8A1EF106764D}resource=/crio_Mod2/AI3;0;ReadMethodType=i16{A5D3F63D-55D4-4299-BD32-365C6EDC0E1B}resource=/crio_Mod2/AI2;0;ReadMethodType=i16{AC1144EE-779F-4666-B501-4A6B15FCC384}resource=/crio_Mod2/AI0;0;ReadMethodType=i16{BF136EDF-437A-4F06-B12C-5E22FCB6FB75}resource=/crio_Mod2/AI1;0;ReadMethodType=i16{D62CCF0E-F129-40EB-B129-39E4EF7661FA}resource=/crio_Mod1/AI3;0;ReadMethodType=i16{F1E88F61-822E-4745-9F73-C6D7D27B1E96}resource=/crio_Mod1/AI0;0;ReadMethodType=i16{F4F4C361-713B-4123-A0A4-5342A8EF2E0A}resource=/crio_Mod1/AI1;0;ReadMethodType=i16{F765A35B-7B7F-404C-B3DB-FD04C90F5402}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 1,crio.Type=NI 9222[crioConfig.End]{FCB66385-7FCA-426B-8137-61FB4229EBA5}resource=/crio_Mod3/AI0;0;ReadMethodType=i16{FE4EAAEC-FFE3-4A22-A213-31B9E24A0E9E}resource=/Scan Clock;0;ReadMethodType=boolcRIO-9067/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9067FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA/[rSeriesConfig.Begin][rSeriesConfig.End]</Property>
						<Property Name="configString.name" Type="Str">40 MHz Onboard ClockResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427E;Chassis Temperatureresource=/Chassis Temperature;0;ReadMethodType=i16cRIO-9067/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9067FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA/[rSeriesConfig.Begin][rSeriesConfig.End]FIFO"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"Mod1/AI0resource=/crio_Mod1/AI0;0;ReadMethodType=i16Mod1/AI1resource=/crio_Mod1/AI1;0;ReadMethodType=i16Mod1/AI2resource=/crio_Mod1/AI2;0;ReadMethodType=i16Mod1/AI3resource=/crio_Mod1/AI3;0;ReadMethodType=i16Mod1[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 1,crio.Type=NI 9222[crioConfig.End]Mod2/AI0resource=/crio_Mod2/AI0;0;ReadMethodType=i16Mod2/AI1resource=/crio_Mod2/AI1;0;ReadMethodType=i16Mod2/AI2resource=/crio_Mod2/AI2;0;ReadMethodType=i16Mod2/AI3resource=/crio_Mod2/AI3;0;ReadMethodType=i16Mod2[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 2,crio.Type=NI 9222[crioConfig.End]Mod3/AI0resource=/crio_Mod3/AI0;0;ReadMethodType=i16Mod3/AI1resource=/crio_Mod3/AI1;0;ReadMethodType=i16Mod3/AI2resource=/crio_Mod3/AI2;0;ReadMethodType=i16Mod3/AI3resource=/crio_Mod3/AI3;0;ReadMethodType=i16Mod3[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 3,crio.Type=NI 9222[crioConfig.End]Scan Clockresource=/Scan Clock;0;ReadMethodType=boolSleepresource=/Sleep;0;ReadMethodType=bool;WriteMethodType=boolSystem Resetresource=/System Reset;0;ReadMethodType=bool;WriteMethodType=boolUSER FPGA LEDArbitrationForOutputData=NeverArbitrate;NumberOfSyncRegistersForOutputData=1;NumberOfSyncRegistersForReadInProject=Auto;resource=/USER FPGA LED;0;ReadMethodType=bool;WriteMethodType=bool</Property>
					</Item>
					<Item Name="Dependencies" Type="Dependencies">
						<Item Name="vi.lib" Type="Folder">
							<Item Name="lvSimController.dll" Type="Document" URL="/&lt;vilib&gt;/rvi/Simulation/lvSimController.dll"/>
						</Item>
					</Item>
					<Item Name="Build Specifications" Type="Build">
						<Item Name="Crio9067_8chan" Type="{F4C5E96F-7410-48A5-BB87-3559BC9B167F}">
							<Property Name="AllowEnableRemoval" Type="Bool">false</Property>
							<Property Name="BuildSpecDecription" Type="Str"></Property>
							<Property Name="BuildSpecName" Type="Str">Crio9067_8chan</Property>
							<Property Name="Comp.BitfileName" Type="Str">cRioProject1_FPGATarget_Crio90678chan_8t+xhQj0Qlg.lvbitx</Property>
							<Property Name="Comp.CustomXilinxParameters" Type="Str"></Property>
							<Property Name="Comp.MaxFanout" Type="Int">-1</Property>
							<Property Name="Comp.RandomSeed" Type="Bool">false</Property>
							<Property Name="Comp.Version.Build" Type="Int">0</Property>
							<Property Name="Comp.Version.Fix" Type="Int">0</Property>
							<Property Name="Comp.Version.Major" Type="Int">1</Property>
							<Property Name="Comp.Version.Minor" Type="Int">0</Property>
							<Property Name="Comp.VersionAutoIncrement" Type="Bool">false</Property>
							<Property Name="Comp.Vivado.EnableMultiThreading" Type="Bool">true</Property>
							<Property Name="Comp.Vivado.OptDirective" Type="Str"></Property>
							<Property Name="Comp.Vivado.PhysOptDirective" Type="Str"></Property>
							<Property Name="Comp.Vivado.PlaceDirective" Type="Str"></Property>
							<Property Name="Comp.Vivado.RouteDirective" Type="Str"></Property>
							<Property Name="Comp.Vivado.RunPowerOpt" Type="Bool">false</Property>
							<Property Name="Comp.Vivado.Strategy" Type="Str">Default</Property>
							<Property Name="Comp.Xilinx.DesignStrategy" Type="Str">balanced</Property>
							<Property Name="Comp.Xilinx.MapEffort" Type="Str">default(noTiming)</Property>
							<Property Name="Comp.Xilinx.ParEffort" Type="Str">standard</Property>
							<Property Name="Comp.Xilinx.SynthEffort" Type="Str">normal</Property>
							<Property Name="Comp.Xilinx.SynthGoal" Type="Str">speed</Property>
							<Property Name="Comp.Xilinx.UseRecommended" Type="Bool">true</Property>
							<Property Name="DefaultBuildSpec" Type="Bool">true</Property>
							<Property Name="DestinationDirectory" Type="Path">FPGA Bitfiles</Property>
							<Property Name="NI.LV.FPGA.LastCompiledBitfilePath" Type="Path">/C/PLABuoy/LabView8ch/FPGA Bitfiles/cRioProject1_FPGATarget_Crio90678chan_8t+xhQj0Qlg.lvbitx</Property>
							<Property Name="NI.LV.FPGA.LastCompiledBitfilePathRelativeToProject" Type="Path">FPGA Bitfiles/cRioProject1_FPGATarget_Crio90678chan_8t+xhQj0Qlg.lvbitx</Property>
							<Property Name="ProjectPath" Type="Path">/C/PLABuoy/LabView8ch/cRio_Project_1.lvproj</Property>
							<Property Name="RelativePath" Type="Bool">true</Property>
							<Property Name="RunWhenLoaded" Type="Bool">false</Property>
							<Property Name="SupportDownload" Type="Bool">true</Property>
							<Property Name="SupportResourceEstimation" Type="Bool">false</Property>
							<Property Name="TargetName" Type="Str">FPGA Target</Property>
							<Property Name="TopLevelVI" Type="Ref">/Crio9067/NI-cRIO-9067-0307a8d1/Chassis/FPGA Target/Crio9067_8chan.vi</Property>
						</Item>
						<Item Name="Crio9067_12chan" Type="{F4C5E96F-7410-48A5-BB87-3559BC9B167F}">
							<Property Name="AllowEnableRemoval" Type="Bool">false</Property>
							<Property Name="BuildSpecDecription" Type="Str"></Property>
							<Property Name="BuildSpecName" Type="Str">Crio9067_12chan</Property>
							<Property Name="Comp.BitfileName" Type="Str">cRioProject1_FPGATarget_Crio906712chan_2xe3Gr76Oa0.lvbitx</Property>
							<Property Name="Comp.CustomXilinxParameters" Type="Str"></Property>
							<Property Name="Comp.MaxFanout" Type="Int">-1</Property>
							<Property Name="Comp.RandomSeed" Type="Bool">false</Property>
							<Property Name="Comp.Version.Build" Type="Int">0</Property>
							<Property Name="Comp.Version.Fix" Type="Int">0</Property>
							<Property Name="Comp.Version.Major" Type="Int">1</Property>
							<Property Name="Comp.Version.Minor" Type="Int">0</Property>
							<Property Name="Comp.VersionAutoIncrement" Type="Bool">false</Property>
							<Property Name="Comp.Vivado.EnableMultiThreading" Type="Bool">true</Property>
							<Property Name="Comp.Vivado.OptDirective" Type="Str"></Property>
							<Property Name="Comp.Vivado.PhysOptDirective" Type="Str"></Property>
							<Property Name="Comp.Vivado.PlaceDirective" Type="Str"></Property>
							<Property Name="Comp.Vivado.RouteDirective" Type="Str"></Property>
							<Property Name="Comp.Vivado.RunPowerOpt" Type="Bool">false</Property>
							<Property Name="Comp.Vivado.Strategy" Type="Str">Default</Property>
							<Property Name="Comp.Xilinx.DesignStrategy" Type="Str">balanced</Property>
							<Property Name="Comp.Xilinx.MapEffort" Type="Str">default(noTiming)</Property>
							<Property Name="Comp.Xilinx.ParEffort" Type="Str">standard</Property>
							<Property Name="Comp.Xilinx.SynthEffort" Type="Str">normal</Property>
							<Property Name="Comp.Xilinx.SynthGoal" Type="Str">speed</Property>
							<Property Name="Comp.Xilinx.UseRecommended" Type="Bool">true</Property>
							<Property Name="DefaultBuildSpec" Type="Bool">true</Property>
							<Property Name="DestinationDirectory" Type="Path">FPGA Bitfiles</Property>
							<Property Name="NI.LV.FPGA.LastCompiledBitfilePath" Type="Path">/C/PLABuoy/LabView8ch/FPGA Bitfiles/cRioProject1_FPGATarget_Crio906712chan_2xe3Gr76Oa0.lvbitx</Property>
							<Property Name="NI.LV.FPGA.LastCompiledBitfilePathRelativeToProject" Type="Path">FPGA Bitfiles/cRioProject1_FPGATarget_Crio906712chan_2xe3Gr76Oa0.lvbitx</Property>
							<Property Name="ProjectPath" Type="Path">/C/PLABuoy/LabView8ch/cRio_Project_1.lvproj</Property>
							<Property Name="RelativePath" Type="Bool">true</Property>
							<Property Name="RunWhenLoaded" Type="Bool">false</Property>
							<Property Name="SupportDownload" Type="Bool">true</Property>
							<Property Name="SupportResourceEstimation" Type="Bool">false</Property>
							<Property Name="TargetName" Type="Str">FPGA Target</Property>
							<Property Name="TopLevelVI" Type="Ref">/Crio9067/NI-cRIO-9067-0307a8d1/Chassis/FPGA Target/Crio9067_12chan.vi</Property>
						</Item>
						<Item Name="Crio9067_4chan" Type="{F4C5E96F-7410-48A5-BB87-3559BC9B167F}">
							<Property Name="AllowEnableRemoval" Type="Bool">false</Property>
							<Property Name="BuildSpecDecription" Type="Str"></Property>
							<Property Name="BuildSpecName" Type="Str">Crio9067_4chan</Property>
							<Property Name="Comp.BitfileName" Type="Str">cRioProject1_FPGATarget_Crio90674chan_0--OX-bs750.lvbitx</Property>
							<Property Name="Comp.CustomXilinxParameters" Type="Str"></Property>
							<Property Name="Comp.MaxFanout" Type="Int">-1</Property>
							<Property Name="Comp.RandomSeed" Type="Bool">false</Property>
							<Property Name="Comp.Version.Build" Type="Int">0</Property>
							<Property Name="Comp.Version.Fix" Type="Int">0</Property>
							<Property Name="Comp.Version.Major" Type="Int">1</Property>
							<Property Name="Comp.Version.Minor" Type="Int">0</Property>
							<Property Name="Comp.VersionAutoIncrement" Type="Bool">false</Property>
							<Property Name="Comp.Vivado.EnableMultiThreading" Type="Bool">true</Property>
							<Property Name="Comp.Vivado.OptDirective" Type="Str"></Property>
							<Property Name="Comp.Vivado.PhysOptDirective" Type="Str"></Property>
							<Property Name="Comp.Vivado.PlaceDirective" Type="Str"></Property>
							<Property Name="Comp.Vivado.RouteDirective" Type="Str"></Property>
							<Property Name="Comp.Vivado.RunPowerOpt" Type="Bool">false</Property>
							<Property Name="Comp.Vivado.Strategy" Type="Str">Default</Property>
							<Property Name="Comp.Xilinx.DesignStrategy" Type="Str">balanced</Property>
							<Property Name="Comp.Xilinx.MapEffort" Type="Str">default(noTiming)</Property>
							<Property Name="Comp.Xilinx.ParEffort" Type="Str">standard</Property>
							<Property Name="Comp.Xilinx.SynthEffort" Type="Str">normal</Property>
							<Property Name="Comp.Xilinx.SynthGoal" Type="Str">speed</Property>
							<Property Name="Comp.Xilinx.UseRecommended" Type="Bool">true</Property>
							<Property Name="DefaultBuildSpec" Type="Bool">true</Property>
							<Property Name="DestinationDirectory" Type="Path">FPGA Bitfiles</Property>
							<Property Name="NI.LV.FPGA.LastCompiledBitfilePath" Type="Path">/C/PLABuoy/LabView8ch/FPGA Bitfiles/cRioProject1_FPGATarget_Crio90674chan_0--OX-bs750.lvbitx</Property>
							<Property Name="NI.LV.FPGA.LastCompiledBitfilePathRelativeToProject" Type="Path">FPGA Bitfiles/cRioProject1_FPGATarget_Crio90674chan_0--OX-bs750.lvbitx</Property>
							<Property Name="ProjectPath" Type="Path">/C/PLABuoy/LabView8ch/cRio_Project_1.lvproj</Property>
							<Property Name="RelativePath" Type="Bool">true</Property>
							<Property Name="RunWhenLoaded" Type="Bool">false</Property>
							<Property Name="SupportDownload" Type="Bool">true</Property>
							<Property Name="SupportResourceEstimation" Type="Bool">false</Property>
							<Property Name="TargetName" Type="Str">FPGA Target</Property>
							<Property Name="TopLevelVI" Type="Ref">/Crio9067/NI-cRIO-9067-0307a8d1/Chassis/FPGA Target/Crio9067_4chan.vi</Property>
						</Item>
					</Item>
				</Item>
			</Item>
			<Item Name="Dependencies" Type="Dependencies"/>
			<Item Name="Build Specifications" Type="Build"/>
		</Item>
	</Item>
	<Item Name="My Computer" Type="My Computer">
		<Property Name="server.app.propertiesEnabled" Type="Bool">true</Property>
		<Property Name="server.control.propertiesEnabled" Type="Bool">true</Property>
		<Property Name="server.tcp.enabled" Type="Bool">false</Property>
		<Property Name="server.tcp.port" Type="Int">0</Property>
		<Property Name="server.tcp.serviceName" Type="Str">My Computer/VI Server</Property>
		<Property Name="server.tcp.serviceName.default" Type="Str">My Computer/VI Server</Property>
		<Property Name="server.vi.callsEnabled" Type="Bool">true</Property>
		<Property Name="server.vi.propertiesEnabled" Type="Bool">true</Property>
		<Property Name="specify.custom.address" Type="Bool">false</Property>
		<Item Name="Dependencies" Type="Dependencies"/>
		<Item Name="Build Specifications" Type="Build"/>
	</Item>
	<Item Name="cRioKE 2" Type="RT CompactRIO">
		<Property Name="alias.name" Type="Str">cRioKE 2</Property>
		<Property Name="alias.value" Type="Str">192.168.2.211</Property>
		<Property Name="CCSymbols" Type="Str">TARGET_TYPE,RT;OS,Linux;CPU,ARM;DeviceCode,76D6;</Property>
		<Property Name="crio.ControllerPID" Type="Str">76D6</Property>
		<Property Name="crio.family" Type="Str">ARMLinux</Property>
		<Property Name="host.ResponsivenessCheckEnabled" Type="Bool">true</Property>
		<Property Name="host.ResponsivenessCheckPingDelay" Type="UInt">5000</Property>
		<Property Name="host.ResponsivenessCheckPingTimeout" Type="UInt">1000</Property>
		<Property Name="host.TargetCPUID" Type="UInt">8</Property>
		<Property Name="host.TargetOSID" Type="UInt">8</Property>
		<Property Name="target.cleanupVisa" Type="Bool">false</Property>
		<Property Name="target.FPProtocolGlobals_ControlTimeLimit" Type="Int">300</Property>
		<Property Name="target.getDefault-&gt;WebServer.Port" Type="Int">80</Property>
		<Property Name="target.getDefault-&gt;WebServer.Timeout" Type="Int">60</Property>
		<Property Name="target.IOScan.Faults" Type="Str"></Property>
		<Property Name="target.IOScan.NetVarPeriod" Type="UInt">100</Property>
		<Property Name="target.IOScan.NetWatchdogEnabled" Type="Bool">false</Property>
		<Property Name="target.IOScan.Period" Type="UInt">10000</Property>
		<Property Name="target.IOScan.PowerupMode" Type="UInt">0</Property>
		<Property Name="target.IOScan.Priority" Type="UInt">0</Property>
		<Property Name="target.IOScan.ReportModeConflict" Type="Bool">true</Property>
		<Property Name="target.IsRemotePanelSupported" Type="Bool">true</Property>
		<Property Name="target.RTCPULoadMonitoringEnabled" Type="Bool">true</Property>
		<Property Name="target.RTDebugWebServerHTTPPort" Type="Int">8001</Property>
		<Property Name="target.RTTarget.ApplicationPath" Type="Path">/c/ni-rt/startup/startup.rtexe</Property>
		<Property Name="target.RTTarget.EnableFileSharing" Type="Bool">true</Property>
		<Property Name="target.RTTarget.IPAccess" Type="Str">+*</Property>
		<Property Name="target.RTTarget.LaunchAppAtBoot" Type="Bool">false</Property>
		<Property Name="target.RTTarget.VIPath" Type="Path">/home/lvuser/natinst/bin</Property>
		<Property Name="target.server.app.propertiesEnabled" Type="Bool">true</Property>
		<Property Name="target.server.control.propertiesEnabled" Type="Bool">true</Property>
		<Property Name="target.server.tcp.access" Type="Str">+*</Property>
		<Property Name="target.server.tcp.enabled" Type="Bool">false</Property>
		<Property Name="target.server.tcp.paranoid" Type="Bool">true</Property>
		<Property Name="target.server.tcp.port" Type="Int">3363</Property>
		<Property Name="target.server.tcp.serviceName" Type="Str">Main Application Instance/VI Server</Property>
		<Property Name="target.server.tcp.serviceName.default" Type="Str">Main Application Instance/VI Server</Property>
		<Property Name="target.server.vi.access" Type="Str">+*</Property>
		<Property Name="target.server.vi.callsEnabled" Type="Bool">true</Property>
		<Property Name="target.server.vi.propertiesEnabled" Type="Bool">true</Property>
		<Property Name="target.WebServer.Config" Type="Str">Listen 8000

NI.ServerName default
DocumentRoot "$LVSERVER_DOCROOT"
TypesConfig "$LVSERVER_CONFIGROOT/mime.types"
DirectoryIndex index.htm
WorkerLimit 10
InactivityTimeout 60

LoadModulePath "$LVSERVER_MODULEPATHS"
LoadModule LVAuth lvauthmodule
LoadModule LVRFP lvrfpmodule

#
# Pipeline Definition
#

SetConnector netConnector

AddHandler LVAuth
AddHandler LVRFP

AddHandler fileHandler ""

AddOutputFilter chunkFilter


</Property>
		<Property Name="target.WebServer.Enabled" Type="Bool">false</Property>
		<Property Name="target.WebServer.LogEnabled" Type="Bool">false</Property>
		<Property Name="target.WebServer.LogPath" Type="Path">/c/ni-rt/system/www/www.log</Property>
		<Property Name="target.WebServer.Port" Type="Int">80</Property>
		<Property Name="target.WebServer.RootPath" Type="Path">/c/ni-rt/system/www</Property>
		<Property Name="target.WebServer.TcpAccess" Type="Str">c+*</Property>
		<Property Name="target.WebServer.Timeout" Type="Int">60</Property>
		<Property Name="target.WebServer.ViAccess" Type="Str">+*</Property>
		<Property Name="target.webservices.SecurityAPIKey" Type="Str">PqVr/ifkAQh+lVrdPIykXlFvg12GhhQFR8H9cUhphgg=:pTe9HRlQuMfJxAG6QCGq7UvoUpJzAzWGKy5SbZ+roSU=</Property>
		<Property Name="target.webservices.ValidTimestampWindow" Type="Int">15</Property>
		<Item Name="Chassis" Type="cRIO Chassis">
			<Property Name="crio.ProgrammingMode" Type="Str">fpga</Property>
			<Property Name="crio.ResourceID" Type="Str">RIO0</Property>
			<Property Name="crio.Type" Type="Str">cRIO-9068</Property>
			<Item Name="FPGA Target 2" Type="FPGA Target">
				<Property Name="AutoRun" Type="Bool">false</Property>
				<Property Name="configString.guid" Type="Str">{09062505-5F2A-4703-80BE-A11E567E1B98}"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"{0C187BA7-2445-42EC-9BA6-076799E4A7D9}resource=/crio_Mod2/AI2;0;ReadMethodType=i16{260C8EE6-5DC4-4AB4-87E0-1DADE31430E2}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 1,crio.Type=NI 9222[crioConfig.End]{47F701C2-A614-4B6A-BDF9-0E949D5B92EE}resource=/crio_Mod1/AI3;0;ReadMethodType=i16{4CDAA015-2E2D-43FB-91F8-48C8A8536F7B}resource=/crio_Mod2/AI3;0;ReadMethodType=i16{5F8E586F-A30D-45AF-8D04-30E022B7476D}resource=/crio_Mod2/AI0;0;ReadMethodType=i16{5FCA222B-CF6D-4F7A-9D05-896FFA82A386}resource=/USER FPGA LED;0;ReadMethodType=u8;WriteMethodType=u8{681DE126-18FB-4D43-B1A8-E925F119B043}resource=/crio_Mod1/AI0;0;ReadMethodType=i16{6D799570-03FD-41E9-BFB6-5303EE954EEA}resource=/crio_Mod1/AI2;0;ReadMethodType=i16{829E3409-C9A7-4949-9AA7-F075637A6415}resource=/crio_Mod1/AI1;0;ReadMethodType=i16{8DF60BDF-DB42-4D12-9B80-808900F9983A}resource=/Sleep;0;ReadMethodType=bool;WriteMethodType=bool{AECFB9EE-36F3-4CAA-BE21-B0C3588B9BC1}resource=/crio_Mod2/AI1;0;ReadMethodType=i16{C9DDA5FF-FE61-4D11-A6BC-2D50B6770C1E}resource=/Scan Clock;0;ReadMethodType=bool{D50B2D5B-7A70-43E6-847B-9FB1EA3FA7CC}resource=/System Reset;0;ReadMethodType=bool;WriteMethodType=bool{D64241E4-3D16-40C0-BBC3-1C610BAC5486}resource=/Chassis Temperature;0;ReadMethodType=i16{F736DA02-9C4D-44EA-80F5-D848968A9CE6}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 2,crio.Type=NI 9222[crioConfig.End]{F8945B2D-48B3-402E-94D3-3C941EC0384B}ResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427EcRIO-9068/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9068FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA/[rSeriesConfig.Begin][rSeriesConfig.End]</Property>
				<Property Name="configString.name" Type="Str">40 MHz Onboard ClockResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427EChassis Temperatureresource=/Chassis Temperature;0;ReadMethodType=i16cRIO-9068/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9068FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA/[rSeriesConfig.Begin][rSeriesConfig.End]FIFO"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"Mod1/AI0resource=/crio_Mod1/AI0;0;ReadMethodType=i16Mod1/AI1resource=/crio_Mod1/AI1;0;ReadMethodType=i16Mod1/AI2resource=/crio_Mod1/AI2;0;ReadMethodType=i16Mod1/AI3resource=/crio_Mod1/AI3;0;ReadMethodType=i16Mod1[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 1,crio.Type=NI 9222[crioConfig.End]Mod2/AI0resource=/crio_Mod2/AI0;0;ReadMethodType=i16Mod2/AI1resource=/crio_Mod2/AI1;0;ReadMethodType=i16Mod2/AI2resource=/crio_Mod2/AI2;0;ReadMethodType=i16Mod2/AI3resource=/crio_Mod2/AI3;0;ReadMethodType=i16Mod2[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 2,crio.Type=NI 9222[crioConfig.End]Scan Clockresource=/Scan Clock;0;ReadMethodType=boolSleepresource=/Sleep;0;ReadMethodType=bool;WriteMethodType=boolSystem Resetresource=/System Reset;0;ReadMethodType=bool;WriteMethodType=boolUSER FPGA LEDresource=/USER FPGA LED;0;ReadMethodType=u8;WriteMethodType=u8</Property>
				<Property Name="Mode" Type="Int">0</Property>
				<Property Name="NI.LV.FPGA.CLIPDeclarationsArraySize" Type="Int">0</Property>
				<Property Name="NI.LV.FPGA.CLIPDeclarationSet" Type="Xml">
<CLIPDeclarationSet>
</CLIPDeclarationSet></Property>
				<Property Name="NI.LV.FPGA.CompileConfigString" Type="Str">cRIO-9068/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9068FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA</Property>
				<Property Name="NI.LV.FPGA.Version" Type="Int">6</Property>
				<Property Name="NI.SortType" Type="Int">3</Property>
				<Property Name="Resource Name" Type="Str">RIO0</Property>
				<Property Name="SWEmulationSubMode" Type="UInt">0</Property>
				<Property Name="SWEmulationVIPath" Type="Path"></Property>
				<Property Name="Target Class" Type="Str">cRIO-9068</Property>
				<Property Name="Top-Level Timing Source" Type="Str">40 MHz Onboard Clock</Property>
				<Property Name="Top-Level Timing Source Is Default" Type="Bool">true</Property>
				<Item Name="Chassis I/O" Type="Folder">
					<Item Name="Chassis Temperature" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/Chassis Temperature</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{D64241E4-3D16-40C0-BBC3-1C610BAC5486}</Property>
					</Item>
					<Item Name="Scan Clock" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/Scan Clock</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{C9DDA5FF-FE61-4D11-A6BC-2D50B6770C1E}</Property>
					</Item>
					<Item Name="Sleep" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/Sleep</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{8DF60BDF-DB42-4D12-9B80-808900F9983A}</Property>
					</Item>
					<Item Name="System Reset" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/System Reset</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{D50B2D5B-7A70-43E6-847B-9FB1EA3FA7CC}</Property>
					</Item>
					<Item Name="USER FPGA LED" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/USER FPGA LED</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{5FCA222B-CF6D-4F7A-9D05-896FFA82A386}</Property>
					</Item>
				</Item>
				<Item Name="Mod1" Type="Folder">
					<Item Name="Mod1/AI0" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod1/AI0</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{681DE126-18FB-4D43-B1A8-E925F119B043}</Property>
					</Item>
					<Item Name="Mod1/AI1" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod1/AI1</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{829E3409-C9A7-4949-9AA7-F075637A6415}</Property>
					</Item>
					<Item Name="Mod1/AI2" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod1/AI2</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{6D799570-03FD-41E9-BFB6-5303EE954EEA}</Property>
					</Item>
					<Item Name="Mod1/AI3" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod1/AI3</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{47F701C2-A614-4B6A-BDF9-0E949D5B92EE}</Property>
					</Item>
				</Item>
				<Item Name="Mod2" Type="Folder">
					<Item Name="Mod2/AI0" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod2/AI0</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{5F8E586F-A30D-45AF-8D04-30E022B7476D}</Property>
					</Item>
					<Item Name="Mod2/AI1" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod2/AI1</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{AECFB9EE-36F3-4CAA-BE21-B0C3588B9BC1}</Property>
					</Item>
					<Item Name="Mod2/AI2" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod2/AI2</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{0C187BA7-2445-42EC-9BA6-076799E4A7D9}</Property>
					</Item>
					<Item Name="Mod2/AI3" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod2/AI3</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{4CDAA015-2E2D-43FB-91F8-48C8A8536F7B}</Property>
					</Item>
				</Item>
				<Item Name="40 MHz Onboard Clock" Type="FPGA Base Clock">
					<Property Name="FPGA.PersistentID" Type="Str">{F8945B2D-48B3-402E-94D3-3C941EC0384B}</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig" Type="Str">ResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427E</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.Accuracy" Type="Dbl">100</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.ClockSignalName" Type="Str">Clk40</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.MaxDutyCycle" Type="Dbl">50</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.MaxFrequency" Type="Dbl">40000000</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.MinDutyCycle" Type="Dbl">50</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.MinFrequency" Type="Dbl">40000000</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.NominalFrequency" Type="Dbl">40000000</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.PeakPeriodJitter" Type="Dbl">250</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.ResourceName" Type="Str">40 MHz Onboard Clock</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.SupportAndRequireRuntimeEnableDisable" Type="Bool">false</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.TopSignalConnect" Type="Str">Clk40</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.VariableFrequency" Type="Bool">false</Property>
					<Property Name="NI.LV.FPGA.Valid" Type="Bool">true</Property>
					<Property Name="NI.LV.FPGA.Version" Type="Int">5</Property>
				</Item>
				<Item Name="FIFO" Type="FPGA FIFO">
					<Property Name="Actual Number of Elements" Type="UInt">8191</Property>
					<Property Name="Arbitration for Read" Type="UInt">1</Property>
					<Property Name="Arbitration for Write" Type="UInt">1</Property>
					<Property Name="Control Logic" Type="UInt">0</Property>
					<Property Name="Data Type" Type="UInt">2</Property>
					<Property Name="Disable on Overflow/Underflow" Type="Bool">false</Property>
					<Property Name="fifo.configuration" Type="Str">"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"</Property>
					<Property Name="fifo.configured" Type="Bool">true</Property>
					<Property Name="fifo.projectItemValid" Type="Bool">true</Property>
					<Property Name="fifo.valid" Type="Bool">true</Property>
					<Property Name="fifo.version" Type="Int">12</Property>
					<Property Name="FPGA.PersistentID" Type="Str">{09062505-5F2A-4703-80BE-A11E567E1B98}</Property>
					<Property Name="Local" Type="Bool">false</Property>
					<Property Name="Memory Type" Type="UInt">2</Property>
					<Property Name="Number Of Elements Per Read" Type="UInt">1</Property>
					<Property Name="Number Of Elements Per Write" Type="UInt">1</Property>
					<Property Name="Requested Number of Elements" Type="UInt">4096</Property>
					<Property Name="Type" Type="UInt">2</Property>
					<Property Name="Type Descriptor" Type="Str">100080000000000100094002000349313600010000000000000000</Property>
				</Item>
				<Item Name="NI_9222_Anologue_DAQ2_FPGA.vi" Type="VI" URL="../NI_9222_Anologue_DAQ2_FPGA.vi">
					<Property Name="configString.guid" Type="Str">{09062505-5F2A-4703-80BE-A11E567E1B98}"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"{0C187BA7-2445-42EC-9BA6-076799E4A7D9}resource=/crio_Mod2/AI2;0;ReadMethodType=i16{260C8EE6-5DC4-4AB4-87E0-1DADE31430E2}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 1,crio.Type=NI 9222[crioConfig.End]{47F701C2-A614-4B6A-BDF9-0E949D5B92EE}resource=/crio_Mod1/AI3;0;ReadMethodType=i16{4CDAA015-2E2D-43FB-91F8-48C8A8536F7B}resource=/crio_Mod2/AI3;0;ReadMethodType=i16{5F8E586F-A30D-45AF-8D04-30E022B7476D}resource=/crio_Mod2/AI0;0;ReadMethodType=i16{5FCA222B-CF6D-4F7A-9D05-896FFA82A386}resource=/USER FPGA LED;0;ReadMethodType=u8;WriteMethodType=u8{681DE126-18FB-4D43-B1A8-E925F119B043}resource=/crio_Mod1/AI0;0;ReadMethodType=i16{6D799570-03FD-41E9-BFB6-5303EE954EEA}resource=/crio_Mod1/AI2;0;ReadMethodType=i16{829E3409-C9A7-4949-9AA7-F075637A6415}resource=/crio_Mod1/AI1;0;ReadMethodType=i16{8DF60BDF-DB42-4D12-9B80-808900F9983A}resource=/Sleep;0;ReadMethodType=bool;WriteMethodType=bool{AECFB9EE-36F3-4CAA-BE21-B0C3588B9BC1}resource=/crio_Mod2/AI1;0;ReadMethodType=i16{C9DDA5FF-FE61-4D11-A6BC-2D50B6770C1E}resource=/Scan Clock;0;ReadMethodType=bool{D50B2D5B-7A70-43E6-847B-9FB1EA3FA7CC}resource=/System Reset;0;ReadMethodType=bool;WriteMethodType=bool{D64241E4-3D16-40C0-BBC3-1C610BAC5486}resource=/Chassis Temperature;0;ReadMethodType=i16{F736DA02-9C4D-44EA-80F5-D848968A9CE6}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 2,crio.Type=NI 9222[crioConfig.End]{F8945B2D-48B3-402E-94D3-3C941EC0384B}ResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427EcRIO-9068/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9068FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA/[rSeriesConfig.Begin][rSeriesConfig.End]</Property>
					<Property Name="configString.name" Type="Str">40 MHz Onboard ClockResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427EChassis Temperatureresource=/Chassis Temperature;0;ReadMethodType=i16cRIO-9068/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9068FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA/[rSeriesConfig.Begin][rSeriesConfig.End]FIFO"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"Mod1/AI0resource=/crio_Mod1/AI0;0;ReadMethodType=i16Mod1/AI1resource=/crio_Mod1/AI1;0;ReadMethodType=i16Mod1/AI2resource=/crio_Mod1/AI2;0;ReadMethodType=i16Mod1/AI3resource=/crio_Mod1/AI3;0;ReadMethodType=i16Mod1[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 1,crio.Type=NI 9222[crioConfig.End]Mod2/AI0resource=/crio_Mod2/AI0;0;ReadMethodType=i16Mod2/AI1resource=/crio_Mod2/AI1;0;ReadMethodType=i16Mod2/AI2resource=/crio_Mod2/AI2;0;ReadMethodType=i16Mod2/AI3resource=/crio_Mod2/AI3;0;ReadMethodType=i16Mod2[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 2,crio.Type=NI 9222[crioConfig.End]Scan Clockresource=/Scan Clock;0;ReadMethodType=boolSleepresource=/Sleep;0;ReadMethodType=bool;WriteMethodType=boolSystem Resetresource=/System Reset;0;ReadMethodType=bool;WriteMethodType=boolUSER FPGA LEDresource=/USER FPGA LED;0;ReadMethodType=u8;WriteMethodType=u8</Property>
					<Property Name="NI.LV.FPGA.InterfaceBitfile" Type="Str">C:\PLABuoy\LabView8ch\FPGA Bitfiles\cRioProject1_FPGATarget2_NI9222AnologueDA_F41zuEPJdDQ.lvbitx</Property>
				</Item>
				<Item Name="Mod1" Type="RIO C Series Module">
					<Property Name="crio.Calibration" Type="Str">0</Property>
					<Property Name="crio.Location" Type="Str">Slot 1</Property>
					<Property Name="crio.RequiresValidation" Type="Bool">false</Property>
					<Property Name="crio.SDcounterSlaveChannelMask" Type="Str">0</Property>
					<Property Name="crio.SDCounterSlaveMasterSlot" Type="Str">0</Property>
					<Property Name="crio.SDInputFilter" Type="Str">128</Property>
					<Property Name="crio.SupportsDynamicRes" Type="Bool">false</Property>
					<Property Name="crio.Type" Type="Str">NI 9222</Property>
					<Property Name="cRIOModule.DigitalIOMode" Type="Str">0</Property>
					<Property Name="cRIOModule.EnableSpecialtyDigital" Type="Str">false</Property>
					<Property Name="FPGA.PersistentID" Type="Str">{260C8EE6-5DC4-4AB4-87E0-1DADE31430E2}</Property>
				</Item>
				<Item Name="Mod2" Type="RIO C Series Module">
					<Property Name="crio.Calibration" Type="Str">0</Property>
					<Property Name="crio.Location" Type="Str">Slot 2</Property>
					<Property Name="crio.RequiresValidation" Type="Bool">false</Property>
					<Property Name="crio.SDcounterSlaveChannelMask" Type="Str">0</Property>
					<Property Name="crio.SDCounterSlaveMasterSlot" Type="Str">0</Property>
					<Property Name="crio.SDInputFilter" Type="Str">128</Property>
					<Property Name="crio.SupportsDynamicRes" Type="Bool">false</Property>
					<Property Name="crio.Type" Type="Str">NI 9222</Property>
					<Property Name="cRIOModule.DigitalIOMode" Type="Str">0</Property>
					<Property Name="cRIOModule.EnableSpecialtyDigital" Type="Str">false</Property>
					<Property Name="FPGA.PersistentID" Type="Str">{F736DA02-9C4D-44EA-80F5-D848968A9CE6}</Property>
				</Item>
				<Item Name="IP Builder" Type="IP Builder Target">
					<Item Name="Dependencies" Type="Dependencies"/>
					<Item Name="Build Specifications" Type="Build"/>
				</Item>
				<Item Name="Dependencies" Type="Dependencies">
					<Item Name="vi.lib" Type="Folder">
						<Item Name="lvSimController.dll" Type="Document" URL="/&lt;vilib&gt;/rvi/Simulation/lvSimController.dll"/>
					</Item>
				</Item>
				<Item Name="Build Specifications" Type="Build">
					<Item Name="NI_9222_Anologue_DAQ2_FPGA" Type="{F4C5E96F-7410-48A5-BB87-3559BC9B167F}">
						<Property Name="AllowEnableRemoval" Type="Bool">false</Property>
						<Property Name="BuildSpecDecription" Type="Str"></Property>
						<Property Name="BuildSpecName" Type="Str">NI_9222_Anologue_DAQ2_FPGA</Property>
						<Property Name="Comp.BitfileName" Type="Str">cRioProject1_FPGATarget2_NI9222AnologueDA_F41zuEPJdDQ.lvbitx</Property>
						<Property Name="Comp.CustomXilinxParameters" Type="Str"></Property>
						<Property Name="Comp.MaxFanout" Type="Int">-1</Property>
						<Property Name="Comp.RandomSeed" Type="Bool">false</Property>
						<Property Name="Comp.Version.Build" Type="Int">0</Property>
						<Property Name="Comp.Version.Fix" Type="Int">0</Property>
						<Property Name="Comp.Version.Major" Type="Int">1</Property>
						<Property Name="Comp.Version.Minor" Type="Int">0</Property>
						<Property Name="Comp.VersionAutoIncrement" Type="Bool">false</Property>
						<Property Name="Comp.Vivado.EnableMultiThreading" Type="Bool">true</Property>
						<Property Name="Comp.Vivado.OptDirective" Type="Str">Default</Property>
						<Property Name="Comp.Vivado.PhysOptDirective" Type="Str">Default</Property>
						<Property Name="Comp.Vivado.PlaceDirective" Type="Str">Default</Property>
						<Property Name="Comp.Vivado.RouteDirective" Type="Str">Default</Property>
						<Property Name="Comp.Vivado.RunPowerOpt" Type="Bool">false</Property>
						<Property Name="Comp.Vivado.Strategy" Type="Str">Default</Property>
						<Property Name="Comp.Xilinx.DesignStrategy" Type="Str">balanced</Property>
						<Property Name="Comp.Xilinx.MapEffort" Type="Str">high(timing)</Property>
						<Property Name="Comp.Xilinx.ParEffort" Type="Str">standard</Property>
						<Property Name="Comp.Xilinx.SynthEffort" Type="Str">normal</Property>
						<Property Name="Comp.Xilinx.SynthGoal" Type="Str">speed</Property>
						<Property Name="Comp.Xilinx.UseRecommended" Type="Bool">true</Property>
						<Property Name="DefaultBuildSpec" Type="Bool">true</Property>
						<Property Name="DestinationDirectory" Type="Path">FPGA Bitfiles</Property>
						<Property Name="NI.LV.FPGA.LastCompiledBitfilePath" Type="Path">/C/PLABuoy/LabView8ch/FPGA Bitfiles/cRioProject1_FPGATarget2_NI9222AnologueDA_F41zuEPJdDQ.lvbitx</Property>
						<Property Name="NI.LV.FPGA.LastCompiledBitfilePathRelativeToProject" Type="Path">FPGA Bitfiles/cRioProject1_FPGATarget2_NI9222AnologueDA_F41zuEPJdDQ.lvbitx</Property>
						<Property Name="ProjectPath" Type="Path">/C/Users/SMRU T430/Dropbox/NERC_KE_cRio/cRio Code/LabView/cRio_Project_1.lvproj</Property>
						<Property Name="RelativePath" Type="Bool">true</Property>
						<Property Name="RunWhenLoaded" Type="Bool">false</Property>
						<Property Name="SupportDownload" Type="Bool">true</Property>
						<Property Name="SupportResourceEstimation" Type="Bool">true</Property>
						<Property Name="TargetName" Type="Str">FPGA Target 2</Property>
						<Property Name="TopLevelVI" Type="Ref">/cRioKE 2/Chassis/FPGA Target 2/NI_9222_Anologue_DAQ2_FPGA.vi</Property>
					</Item>
				</Item>
			</Item>
		</Item>
		<Item Name="Dependencies" Type="Dependencies"/>
		<Item Name="Build Specifications" Type="Build"/>
	</Item>
	<Item Name="NI-cRIO-9063-01d5629a" Type="RT CompactRIO">
		<Property Name="alias.name" Type="Str">NI-cRIO-9063-01d5629a</Property>
		<Property Name="alias.value" Type="Str">192.168.0.204</Property>
		<Property Name="CCSymbols" Type="Str">TARGET_TYPE,RT;OS,Linux;CPU,ARM;DeviceCode,7740;</Property>
		<Property Name="crio.ControllerPID" Type="Str">7740</Property>
		<Property Name="host.TargetCPUID" Type="UInt">8</Property>
		<Property Name="host.TargetOSID" Type="UInt">8</Property>
		<Property Name="target.IOScan.Faults" Type="Str"></Property>
		<Property Name="target.IOScan.NetVarPeriod" Type="UInt">100</Property>
		<Property Name="target.IOScan.NetWatchdogEnabled" Type="Bool">false</Property>
		<Property Name="target.IOScan.Period" Type="UInt">10000</Property>
		<Property Name="target.IOScan.PowerupMode" Type="UInt">0</Property>
		<Property Name="target.IOScan.Priority" Type="UInt">0</Property>
		<Property Name="target.IOScan.ReportModeConflict" Type="Bool">true</Property>
		<Property Name="target.RTTarget.VIPath" Type="Path">/home/lvuser/natinst/bin</Property>
		<Item Name="Chassis" Type="cRIO Chassis">
			<Property Name="crio.ProgrammingMode" Type="Str">fpga</Property>
			<Property Name="crio.ResourceID" Type="Str">RIO0</Property>
			<Property Name="crio.Type" Type="Str">cRIO-9063</Property>
			<Item Name="FPGA Target 3" Type="FPGA Target">
				<Property Name="AutoRun" Type="Bool">false</Property>
				<Property Name="configString.guid" Type="Str">{05FE1724-971A-4120-8218-FA46F54D5466}resource=/crio_Mod1/AI0;0;ReadMethodType=i16{2A18B52F-800E-4A19-BDDC-4F45AF2B94FA}ResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427E;{2B5F8783-24DC-4454-BC74-E0739998F5F4}resource=/crio_Mod1/AI1;0;ReadMethodType=i16{3B534505-C68F-47EF-BDA8-DC9486B5BBC3}resource=/Sleep;0;ReadMethodType=bool;WriteMethodType=bool{6A349821-50DA-4FA4-B12D-39A045CAC4C4}resource=/crio_Mod1/AI2;0;ReadMethodType=i16{7D394499-5DA5-44BA-8DB0-D7726919B86A}resource=/crio_Mod1/AI3;0;ReadMethodType=i16{9D64D1D7-FC2D-4DCE-9785-C3D8CAA53827}ArbitrationForOutputData=NeverArbitrate;NumberOfSyncRegistersForOutputData=1;NumberOfSyncRegistersForReadInProject=Auto;resource=/USER FPGA LED;0;ReadMethodType=bool;WriteMethodType=bool{B1D245FC-266F-4F18-9E64-09CBF76CACBC}resource=/Scan Clock;0;ReadMethodType=bool{B94CD584-50CC-4358-A986-BBE1A8D05884}resource=/System Reset;0;ReadMethodType=bool;WriteMethodType=bool{C611679B-1CEA-4409-A4A9-77CB6BC6CB30}resource=/Chassis Temperature;0;ReadMethodType=i16{CAF1086F-B5D3-4E42-9CC9-676286823AE2}"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"{E2E46954-21C6-4FBB-B336-30E41238CA35}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 1,crio.Type=NI 9223[crioConfig.End]cRIO-9063/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9063FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA/[rSeriesConfig.Begin][rSeriesConfig.End]</Property>
				<Property Name="configString.name" Type="Str">40 MHz Onboard ClockResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427E;Chassis Temperatureresource=/Chassis Temperature;0;ReadMethodType=i16cRIO-9063/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9063FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA/[rSeriesConfig.Begin][rSeriesConfig.End]FIFO"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"Mod1/AI0resource=/crio_Mod1/AI0;0;ReadMethodType=i16Mod1/AI1resource=/crio_Mod1/AI1;0;ReadMethodType=i16Mod1/AI2resource=/crio_Mod1/AI2;0;ReadMethodType=i16Mod1/AI3resource=/crio_Mod1/AI3;0;ReadMethodType=i16Mod1[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 1,crio.Type=NI 9223[crioConfig.End]Scan Clockresource=/Scan Clock;0;ReadMethodType=boolSleepresource=/Sleep;0;ReadMethodType=bool;WriteMethodType=boolSystem Resetresource=/System Reset;0;ReadMethodType=bool;WriteMethodType=boolUSER FPGA LEDArbitrationForOutputData=NeverArbitrate;NumberOfSyncRegistersForOutputData=1;NumberOfSyncRegistersForReadInProject=Auto;resource=/USER FPGA LED;0;ReadMethodType=bool;WriteMethodType=bool</Property>
				<Property Name="NI.LV.FPGA.CompileConfigString" Type="Str">cRIO-9063/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9063FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA</Property>
				<Property Name="NI.LV.FPGA.Version" Type="Int">6</Property>
				<Property Name="Resource Name" Type="Str">RIO0</Property>
				<Property Name="Target Class" Type="Str">cRIO-9063</Property>
				<Property Name="Top-Level Timing Source" Type="Str">40 MHz Onboard Clock</Property>
				<Property Name="Top-Level Timing Source Is Default" Type="Bool">true</Property>
				<Item Name="Chassis I/O" Type="Folder">
					<Item Name="Chassis Temperature" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/Chassis Temperature</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{C611679B-1CEA-4409-A4A9-77CB6BC6CB30}</Property>
					</Item>
					<Item Name="Scan Clock" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/Scan Clock</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{B1D245FC-266F-4F18-9E64-09CBF76CACBC}</Property>
					</Item>
					<Item Name="Sleep" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/Sleep</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{3B534505-C68F-47EF-BDA8-DC9486B5BBC3}</Property>
					</Item>
					<Item Name="System Reset" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/System Reset</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{B94CD584-50CC-4358-A986-BBE1A8D05884}</Property>
					</Item>
					<Item Name="USER FPGA LED" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="ArbitrationForOutputData">
   <Value>NeverArbitrate</Value>
   </Attribute>
   <Attribute name="NumberOfSyncRegistersForOutputData">
   <Value>1</Value>
   </Attribute>
   <Attribute name="NumberOfSyncRegistersForReadInProject">
   <Value>Auto</Value>
   </Attribute>
   <Attribute name="resource">
   <Value>/USER FPGA LED</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{9D64D1D7-FC2D-4DCE-9785-C3D8CAA53827}</Property>
					</Item>
				</Item>
				<Item Name="Mod1" Type="Folder">
					<Item Name="Mod1/AI0" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod1/AI0</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{05FE1724-971A-4120-8218-FA46F54D5466}</Property>
					</Item>
					<Item Name="Mod1/AI1" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod1/AI1</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{2B5F8783-24DC-4454-BC74-E0739998F5F4}</Property>
					</Item>
					<Item Name="Mod1/AI2" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod1/AI2</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{6A349821-50DA-4FA4-B12D-39A045CAC4C4}</Property>
					</Item>
					<Item Name="Mod1/AI3" Type="Elemental IO">
						<Property Name="eioAttrBag" Type="Xml"><AttributeSet name="">
   <Attribute name="resource">
   <Value>/crio_Mod1/AI3</Value>
   </Attribute>
</AttributeSet>
</Property>
						<Property Name="FPGA.PersistentID" Type="Str">{7D394499-5DA5-44BA-8DB0-D7726919B86A}</Property>
					</Item>
				</Item>
				<Item Name="40 MHz Onboard Clock" Type="FPGA Base Clock">
					<Property Name="FPGA.PersistentID" Type="Str">{2A18B52F-800E-4A19-BDDC-4F45AF2B94FA}</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig" Type="Str">ResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427E;</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.Accuracy" Type="Dbl">100</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.ClockSignalName" Type="Str">Clk40</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.MaxDutyCycle" Type="Dbl">50</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.MaxFrequency" Type="Dbl">40000000</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.MinDutyCycle" Type="Dbl">50</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.MinFrequency" Type="Dbl">40000000</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.NominalFrequency" Type="Dbl">40000000</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.PeakPeriodJitter" Type="Dbl">250</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.ResourceName" Type="Str">40 MHz Onboard Clock</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.SupportAndRequireRuntimeEnableDisable" Type="Bool">false</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.TopSignalConnect" Type="Str">Clk40</Property>
					<Property Name="NI.LV.FPGA.BaseTSConfig.VariableFrequency" Type="Bool">false</Property>
					<Property Name="NI.LV.FPGA.Valid" Type="Bool">true</Property>
					<Property Name="NI.LV.FPGA.Version" Type="Int">5</Property>
				</Item>
				<Item Name="CRio9063_4chan_9223.vi" Type="VI" URL="../CRio9063_4chan_9223.vi">
					<Property Name="configString.guid" Type="Str">{05FE1724-971A-4120-8218-FA46F54D5466}resource=/crio_Mod1/AI0;0;ReadMethodType=i16{2A18B52F-800E-4A19-BDDC-4F45AF2B94FA}ResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427E;{2B5F8783-24DC-4454-BC74-E0739998F5F4}resource=/crio_Mod1/AI1;0;ReadMethodType=i16{3B534505-C68F-47EF-BDA8-DC9486B5BBC3}resource=/Sleep;0;ReadMethodType=bool;WriteMethodType=bool{6A349821-50DA-4FA4-B12D-39A045CAC4C4}resource=/crio_Mod1/AI2;0;ReadMethodType=i16{7D394499-5DA5-44BA-8DB0-D7726919B86A}resource=/crio_Mod1/AI3;0;ReadMethodType=i16{9D64D1D7-FC2D-4DCE-9785-C3D8CAA53827}ArbitrationForOutputData=NeverArbitrate;NumberOfSyncRegistersForOutputData=1;NumberOfSyncRegistersForReadInProject=Auto;resource=/USER FPGA LED;0;ReadMethodType=bool;WriteMethodType=bool{B1D245FC-266F-4F18-9E64-09CBF76CACBC}resource=/Scan Clock;0;ReadMethodType=bool{B94CD584-50CC-4358-A986-BBE1A8D05884}resource=/System Reset;0;ReadMethodType=bool;WriteMethodType=bool{C611679B-1CEA-4409-A4A9-77CB6BC6CB30}resource=/Chassis Temperature;0;ReadMethodType=i16{CAF1086F-B5D3-4E42-9CC9-676286823AE2}"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"{E2E46954-21C6-4FBB-B336-30E41238CA35}[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 1,crio.Type=NI 9223[crioConfig.End]cRIO-9063/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9063FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA/[rSeriesConfig.Begin][rSeriesConfig.End]</Property>
					<Property Name="configString.name" Type="Str">40 MHz Onboard ClockResourceName=40 MHz Onboard Clock;TopSignalConnect=Clk40;ClockSignalName=Clk40;MinFreq=40000000.000000;MaxFreq=40000000.000000;VariableFreq=0;NomFreq=40000000.000000;PeakPeriodJitter=250.000000;MinDutyCycle=50.000000;MaxDutyCycle=50.000000;Accuracy=100.000000;RunTime=0;SpreadSpectrum=0;GenericDataHash=D41D8CD98F00B204E9800998ECF8427E;Chassis Temperatureresource=/Chassis Temperature;0;ReadMethodType=i16cRIO-9063/Clk40/falsefalseFPGA_EXECUTION_MODEFPGA_TARGETFPGA_TARGET_CLASSCRIO_9063FPGA_TARGET_FAMILYZYNQTARGET_TYPEFPGA/[rSeriesConfig.Begin][rSeriesConfig.End]FIFO"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"Mod1/AI0resource=/crio_Mod1/AI0;0;ReadMethodType=i16Mod1/AI1resource=/crio_Mod1/AI1;0;ReadMethodType=i16Mod1/AI2resource=/crio_Mod1/AI2;0;ReadMethodType=i16Mod1/AI3resource=/crio_Mod1/AI3;0;ReadMethodType=i16Mod1[crioConfig.Begin]crio.Calibration=0,crio.Location=Slot 1,crio.Type=NI 9223[crioConfig.End]Scan Clockresource=/Scan Clock;0;ReadMethodType=boolSleepresource=/Sleep;0;ReadMethodType=bool;WriteMethodType=boolSystem Resetresource=/System Reset;0;ReadMethodType=bool;WriteMethodType=boolUSER FPGA LEDArbitrationForOutputData=NeverArbitrate;NumberOfSyncRegistersForOutputData=1;NumberOfSyncRegistersForReadInProject=Auto;resource=/USER FPGA LED;0;ReadMethodType=bool;WriteMethodType=bool</Property>
					<Property Name="NI.LV.FPGA.InterfaceBitfile" Type="Str">C:\PLABuoy\LabView8ch\FPGA Bitfiles\cRioProject1_FPGATarget3_CRio90684chan922_MouDPKnyTMo.lvbitx</Property>
				</Item>
				<Item Name="FIFO" Type="FPGA FIFO">
					<Property Name="Actual Number of Elements" Type="UInt">8191</Property>
					<Property Name="Arbitration for Read" Type="UInt">1</Property>
					<Property Name="Arbitration for Write" Type="UInt">1</Property>
					<Property Name="Control Logic" Type="UInt">0</Property>
					<Property Name="Data Type" Type="UInt">2</Property>
					<Property Name="Disable on Overflow/Underflow" Type="Bool">false</Property>
					<Property Name="fifo.configuration" Type="Str">"ControlLogic=0;NumberOfElements=8191;Type=2;ReadArbs=Arbitrate if Multiple Requestors Only;ElementsPerRead=1;WriteArbs=Arbitrate if Multiple Requestors Only;ElementsPerWrite=1;Implementation=2;FIFO;DataType=100080000000000100094002000349313600010000000000000000;DisableOnOverflowUnderflow=FALSE"</Property>
					<Property Name="fifo.configured" Type="Bool">true</Property>
					<Property Name="fifo.projectItemValid" Type="Bool">true</Property>
					<Property Name="fifo.valid" Type="Bool">true</Property>
					<Property Name="fifo.version" Type="Int">12</Property>
					<Property Name="FPGA.PersistentID" Type="Str">{CAF1086F-B5D3-4E42-9CC9-676286823AE2}</Property>
					<Property Name="Local" Type="Bool">false</Property>
					<Property Name="Memory Type" Type="UInt">2</Property>
					<Property Name="Number Of Elements Per Read" Type="UInt">1</Property>
					<Property Name="Number Of Elements Per Write" Type="UInt">1</Property>
					<Property Name="Requested Number of Elements" Type="UInt">4096</Property>
					<Property Name="Type" Type="UInt">2</Property>
					<Property Name="Type Descriptor" Type="Str">100080000000000100094002000349313600010000000000000000</Property>
				</Item>
				<Item Name="IP Builder" Type="IP Builder Target">
					<Item Name="Dependencies" Type="Dependencies"/>
					<Item Name="Build Specifications" Type="Build"/>
				</Item>
				<Item Name="Mod1" Type="RIO C Series Module">
					<Property Name="crio.Calibration" Type="Str">0</Property>
					<Property Name="crio.Location" Type="Str">Slot 1</Property>
					<Property Name="crio.RequiresValidation" Type="Bool">false</Property>
					<Property Name="crio.SDcounterSlaveChannelMask" Type="Str">0</Property>
					<Property Name="crio.SDCounterSlaveMasterSlot" Type="Str">0</Property>
					<Property Name="crio.SDInputFilter" Type="Str">128</Property>
					<Property Name="crio.SupportsDynamicRes" Type="Bool">false</Property>
					<Property Name="crio.Type" Type="Str">NI 9223</Property>
					<Property Name="cRIOModule.DigitalIOMode" Type="Str">0</Property>
					<Property Name="cRIOModule.EnableSpecialtyDigital" Type="Str">false</Property>
					<Property Name="FPGA.PersistentID" Type="Str">{E2E46954-21C6-4FBB-B336-30E41238CA35}</Property>
				</Item>
				<Item Name="Dependencies" Type="Dependencies"/>
				<Item Name="Build Specifications" Type="Build">
					<Item Name="CRio9068_4chan_9223" Type="{F4C5E96F-7410-48A5-BB87-3559BC9B167F}">
						<Property Name="AllowEnableRemoval" Type="Bool">false</Property>
						<Property Name="BuildSpecDecription" Type="Str"></Property>
						<Property Name="BuildSpecName" Type="Str">CRio9068_4chan_9223</Property>
						<Property Name="Comp.BitfileName" Type="Str">cRioProject1_FPGATarget3_CRio90684chan922_MouDPKnyTMo.lvbitx</Property>
						<Property Name="Comp.CustomXilinxParameters" Type="Str"></Property>
						<Property Name="Comp.MaxFanout" Type="Int">-1</Property>
						<Property Name="Comp.RandomSeed" Type="Bool">false</Property>
						<Property Name="Comp.Version.Build" Type="Int">0</Property>
						<Property Name="Comp.Version.Fix" Type="Int">0</Property>
						<Property Name="Comp.Version.Major" Type="Int">1</Property>
						<Property Name="Comp.Version.Minor" Type="Int">0</Property>
						<Property Name="Comp.VersionAutoIncrement" Type="Bool">false</Property>
						<Property Name="Comp.Vivado.EnableMultiThreading" Type="Bool">true</Property>
						<Property Name="Comp.Vivado.OptDirective" Type="Str"></Property>
						<Property Name="Comp.Vivado.PhysOptDirective" Type="Str"></Property>
						<Property Name="Comp.Vivado.PlaceDirective" Type="Str"></Property>
						<Property Name="Comp.Vivado.RouteDirective" Type="Str"></Property>
						<Property Name="Comp.Vivado.RunPowerOpt" Type="Bool">false</Property>
						<Property Name="Comp.Vivado.Strategy" Type="Str">Default</Property>
						<Property Name="Comp.Xilinx.DesignStrategy" Type="Str">balanced</Property>
						<Property Name="Comp.Xilinx.MapEffort" Type="Str">default(noTiming)</Property>
						<Property Name="Comp.Xilinx.ParEffort" Type="Str">standard</Property>
						<Property Name="Comp.Xilinx.SynthEffort" Type="Str">normal</Property>
						<Property Name="Comp.Xilinx.SynthGoal" Type="Str">speed</Property>
						<Property Name="Comp.Xilinx.UseRecommended" Type="Bool">true</Property>
						<Property Name="DefaultBuildSpec" Type="Bool">true</Property>
						<Property Name="DestinationDirectory" Type="Path">FPGA Bitfiles</Property>
						<Property Name="NI.LV.FPGA.LastCompiledBitfilePath" Type="Path">/C/PLABuoy/LabView8ch/FPGA Bitfiles/cRioProject1_FPGATarget3_CRio90684chan922_MouDPKnyTMo.lvbitx</Property>
						<Property Name="NI.LV.FPGA.LastCompiledBitfilePathRelativeToProject" Type="Path">FPGA Bitfiles/cRioProject1_FPGATarget3_CRio90684chan922_MouDPKnyTMo.lvbitx</Property>
						<Property Name="ProjectPath" Type="Path">/C/PLABuoy/LabView8ch/cRio_Project_1.lvproj</Property>
						<Property Name="RelativePath" Type="Bool">true</Property>
						<Property Name="RunWhenLoaded" Type="Bool">false</Property>
						<Property Name="SupportDownload" Type="Bool">true</Property>
						<Property Name="SupportResourceEstimation" Type="Bool">false</Property>
						<Property Name="TargetName" Type="Str">FPGA Target 3</Property>
						<Property Name="TopLevelVI" Type="Ref">/NI-cRIO-9063-01d5629a/Chassis/FPGA Target 3/CRio9063_4chan_9223.vi</Property>
					</Item>
				</Item>
			</Item>
		</Item>
		<Item Name="Dependencies" Type="Dependencies"/>
		<Item Name="Build Specifications" Type="Build"/>
	</Item>
</Project>
